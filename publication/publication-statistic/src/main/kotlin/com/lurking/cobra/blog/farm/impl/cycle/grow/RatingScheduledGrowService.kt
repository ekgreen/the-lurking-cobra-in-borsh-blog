package com.lurking.cobra.blog.farm.impl.cycle.grow

import com.lurking.cobra.blog.farm.api.cycle.grow.GrowService
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.RatingBasedSchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.RatingBasedSchedulingTaskRepository
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.GrowStrategy
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class RatingScheduledGrowService(
    private val growStrategy: GrowStrategy<RatingBasedSchedulingTask, Double>,
    private val repository: RatingBasedSchedulingTaskRepository<RatingBasedSchedulingTask>
) : GrowService {

    override fun attachPublication(publication: Publication, strategy: PublicationStrategy) {
        val task: RatingBasedSchedulingTask? = publication.id!!.let { repository.findByPublicationIdAndStatus(it) }

        val estimatedTaskDelay = growStrategy.estimateTaskDelay(task, publication.rating, strategy)

        createRatingTask(publication, estimatedTaskDelay).also { repository.save(it) }
    }

    private fun createRatingTask(publication: Publication, estimatedTaskDelay: Long): RatingBasedSchedulingTask {
        return RatingBasedSchedulingTask(
            publicationId = publication.id!!,
            launchTimestamp = LocalDateTime.now().plus(estimatedTaskDelay, ChronoUnit.MILLIS),
            growTime = estimatedTaskDelay,
            growRate = publication.rating
        )
    }
}

