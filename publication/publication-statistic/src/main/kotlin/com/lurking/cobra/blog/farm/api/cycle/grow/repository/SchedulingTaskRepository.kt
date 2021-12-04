package com.lurking.cobra.blog.farm.api.cycle.grow.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.time.LocalDateTime

@NoRepositoryBean
interface SchedulingTaskRepository<T : SchedulingTask> : JpaRepository<T, Long> {

    fun findByPublicationIdAndStatus(publicationId: String, status: TaskStatus = TaskStatus.LAUNCHED): T?

    fun findByLaunchTimestampBeforeAndStatus(before: LocalDateTime, status: TaskStatus = TaskStatus.WAITING): List<T>
}