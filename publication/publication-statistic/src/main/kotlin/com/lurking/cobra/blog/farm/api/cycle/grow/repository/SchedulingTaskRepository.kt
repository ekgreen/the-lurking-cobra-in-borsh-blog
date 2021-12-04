package com.lurking.cobra.blog.farm.api.cycle.grow.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.time.LocalDateTime

@NoRepositoryBean
interface SchedulingTaskRepository<T : SchedulingTask> : JpaRepository<T, Long> {

    fun findByPublicationId(publicationId : String): T?

    fun findByLaunchTimestampBetween(leftBound: LocalDateTime, rightBound: LocalDateTime): List<T>
}