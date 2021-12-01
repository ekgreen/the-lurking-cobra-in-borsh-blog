package com.lurking.cobra.blog.generator.api.publication

import java.time.LocalDateTime

data class Publication(val id: String, val urn: String, var timestamp: LocalDateTime? = null)