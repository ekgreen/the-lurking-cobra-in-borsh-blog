package com.lurking.cobra.blog.farm.api.cycle.grow.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "publication.grow")
data class GrowServiceConfiguration (
    var scheduler: GrowScheduler? = null
)

data class GrowScheduler(
    var delay: Long? = null,
    var threads: Int? = null
)