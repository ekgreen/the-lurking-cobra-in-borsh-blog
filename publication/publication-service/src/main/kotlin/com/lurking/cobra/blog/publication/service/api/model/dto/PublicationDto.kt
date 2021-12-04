package com.lurking.cobra.blog.publication.service.api.model.dto

import com.lurking.cobra.blog.publication.service.api.model.entity.Status
import java.util.*

data class PublicationDto(
    var id: String? = null,
    var uri: String,
    var title: String,
    var rating: Double,
    var tags: MutableSet<String> = mutableSetOf(),
    var key_words: MutableSet<String> = mutableSetOf(),
    var reactions: MutableMap<String, Int> = mutableMapOf(),
    var status: Int,
    var last_publication: Date,
    var publication_count: Int
)