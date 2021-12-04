package com.lurking.cobra.blog.farm.api.cycle.grow.repository

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "rating_scheduling_task", schema = "statistic")
open class RatingBasedSchedulingTask (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    open var status: TaskStatus = TaskStatus.WAITING
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

    override fun changeTaskStatus(status: TaskStatus) {
        this.status = status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RatingBasedSchedulingTask

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , publicationId = $publicationId , launchTimestamp = $launchTimestamp , growTime = $growTime , growRate = $growRate , status = $status )"
    }
}