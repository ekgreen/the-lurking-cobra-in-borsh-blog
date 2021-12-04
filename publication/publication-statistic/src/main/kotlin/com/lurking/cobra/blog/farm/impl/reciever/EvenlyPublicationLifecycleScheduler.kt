package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTaskRepository
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.TaskStatus
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleScheduler
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleSchedulerConfiguration
import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import mu.KLogging
import org.springframework.beans.factory.DisposableBean
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.time.Duration
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.math.max

class EvenlyPublicationLifecycleScheduler(
    private val repository: SchedulingTaskRepository<SchedulingTask>,
    private val scheduler: ScheduledExecutorService,
    private val configuration: PublicationLifecycleSchedulerConfiguration,
    private val publicationHandler: PublicationHandler

) : PublicationLifecycleScheduler, DisposableBean {

    @EventListener(ContextRefreshedEvent::class)
    fun startOnContextRefresh(event: ContextRefreshedEvent) {
        refreshTask(configuration.getSchedulingDelay())
    }

    override fun schedule() {
        val delay = configuration.getSchedulingDelay()
        val start = now()

        try{
            repository.findByLaunchTimestampBeforeAndStatus(start.plus(delay, ChronoUnit.MILLIS)).forEach{ task ->
                beforeLaunch(task)

                scheduler.schedule(
                    {
                        try {
                            publicationHandler.handlePublication(PublicationDto(id = task.publicationId()))
                            afterLaunch(task)
                        }catch (exception: Exception){
                            logger.error(exception) { "Scheduler [log, id = ${task.id()}] task was failed = $task" }
                        }
                    },
                    Duration.between(start, task.launchTimestamp()).toMillis(),
                    TimeUnit.MILLISECONDS
                )

                logger.info { "Scheduler [log, id = ${task.id()}] task launched = $task" }
            }
        }finally {
            refreshTask( max(delay - Duration.between(start, now()).toMillis(), 0) )
        }
    }

    private fun beforeLaunch(task: SchedulingTask) {
        logger.info { "Scheduler [log, id = ${task.id()}] handle waiting task = $task" }
        task.changeTaskStatus(TaskStatus.LAUNCHED)
        repository.save(task)
    }

    private fun afterLaunch(task: SchedulingTask) {
        task.changeTaskStatus(TaskStatus.EXECUTED)
        logger.info { "Scheduler [log, id = ${task.id()}] task success executed = $task" }
        repository.save(task)
    }

    private fun refreshTask(delay: Long) {
        scheduler.schedule(::schedule, delay, TimeUnit.MILLISECONDS)
    }

    override fun destroy() {
        scheduler.shutdown()
        scheduler.awaitTermination(5, TimeUnit.SECONDS)
    }

    companion object: KLogging()
}