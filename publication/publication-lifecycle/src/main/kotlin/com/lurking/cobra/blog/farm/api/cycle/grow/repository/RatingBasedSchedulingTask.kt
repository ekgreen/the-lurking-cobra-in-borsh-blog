package com.lurking.cobra.blog.farm.api.cycle.grow.repository

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "rating_based_scheduling_task")
open class RatingBasedSchedulingTask (
    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @Column(name = "publication_id", nullable = false)
    open var publicationId: String,

    @Column(name = "launch_timestamp", nullable = false)
    open var launchTimestamp: LocalDateTime,

    @Column(name = "grow_time", nullable = false)
    open var growTime: Long,

    @Column(name = "grow_rate", nullable = false)
    open var growRate: Double,
): SchedulingTask {
    override fun id(): Long? {
        return id
    }

    override fun publicationId(): String {
        return publicationId
    }

    override fun launchTimestamp(): LocalDateTime {
        return launchTimestamp
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RatingBasedSchedulingTask

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "RatingBasedSchedulingTask(id=$id, publicationId='$publicationId', launchTimestamp=$launchTimestamp, growTime=$growTime, growRate=$growRate)"
    }
}