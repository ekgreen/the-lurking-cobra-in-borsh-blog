package com.lurking.cobra.blog.generator.api.watcher.habr

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "blog.generator.habr")
data class HabrConfiguration @ConstructorBinding constructor(
    val subscriptions: List<Hub>
)
