package com.lurking.cobra.blog.generator.api.watcher.habr

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "blog.generators.habr")
data class HabrConfiguration(
    private val subscriptions: List<Hub>
)
