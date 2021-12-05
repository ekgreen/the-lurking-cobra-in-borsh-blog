package com.lurking.cobra.blog.farm.api.cycle.grow.repository

import java.time.LocalDateTime

interface SchedulingTask {
    fun id(): Long?
    fun publicationId(): String
    fun launchTimestamp(): LocalDateTime
    fun changeTaskStatus(status: TaskStatus)
}