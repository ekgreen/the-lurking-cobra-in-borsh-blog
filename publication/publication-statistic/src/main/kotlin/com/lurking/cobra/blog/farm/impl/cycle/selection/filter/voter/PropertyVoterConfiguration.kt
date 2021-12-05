package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "publication.selection.filters.voter")
data class PropertyVoterConfiguration @ConstructorBinding constructor(
    val tags: List<RequiredTags>
)

class RequiredTags: HashSet<String>()