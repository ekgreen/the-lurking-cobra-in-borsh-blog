package com.lurking.cobra.blog.generator.impl.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.TailVisitorRule
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class JoinTailVisitorRule(private val rules: List<TailVisitorRule>) : TailVisitorRule {

    override fun isTailPublication(publication: Publication): Boolean {
        return rules.any { it.isTailPublication(publication) }
    }
}