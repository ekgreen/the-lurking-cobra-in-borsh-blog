package com.lurking.cobra.blog.publication.service.api.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Publication")
data class PublicationEntity(
    @Id var id: String? = null,
    var uri: String,
    var title: String,
    var rating: Double,
    var tags: MutableSet<String> = mutableSetOf(),
    var key_words: MutableSet<String> = mutableSetOf(),
    var reactions: MutableMap<String, Int> = mutableMapOf(),
    var status: Status
) {
}

enum class Status {
    GROW, FROZEN, PUBLICATION_READY
}