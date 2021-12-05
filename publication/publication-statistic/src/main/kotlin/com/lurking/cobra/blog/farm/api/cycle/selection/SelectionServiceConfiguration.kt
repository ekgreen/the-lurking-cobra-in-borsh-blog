package com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "publication.selection")
data class SelectionServiceConfiguration (
    var filters: SelectionFilters? = null
)

data class SelectionFilters(
    var threshold: ThresholdFilter? = null,
)

data class ThresholdFilter(
    var publishing: Double? = null,
    var freeze: Double? = null
)