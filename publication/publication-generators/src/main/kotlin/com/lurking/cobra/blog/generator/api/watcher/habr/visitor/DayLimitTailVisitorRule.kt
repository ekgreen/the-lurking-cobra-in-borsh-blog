package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.publication.Publication
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DayLimitTailVisitorRule(private val days: Long) : TailVisitorRule {

    override fun isTailPublication(publication: Publication): Boolean {
        return publication.timestamp!!.plus(days, ChronoUnit.DAYS).isAfter(LocalDateTime.now())
    }
}