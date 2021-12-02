package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTaskRepository
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleScheduler
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleSchedulerConfiguration
import com.lurking.cobra.blog.farm.api.handler.PublicationRequest
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
    private val repositories: SchedulingTaskRepository<out SchedulingTask>,
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
            repositories.findByLaunchTimestampBetween(start, start.plus(delay, ChronoUnit.MILLIS)).forEach{ task ->
                scheduler.schedule({publicationHandler.handlePublication(PublicationRequest(id = task.publicationId()))}, Duration.between(start, task.launchTimestamp()).toMillis(), TimeUnit.MILLISECONDS)
            }
        }finally {
            refreshTask( max(delay - Duration.between(start, now()).toMillis(), 0) )
        }
    }

    private fun refreshTask(delay: Long) {
        scheduler.schedule(::schedule, delay, TimeUnit.MILLISECONDS)
    }

    override fun destroy() {
        scheduler.shutdown()
        scheduler.awaitTermination(5, TimeUnit.SECONDS)
    }

}