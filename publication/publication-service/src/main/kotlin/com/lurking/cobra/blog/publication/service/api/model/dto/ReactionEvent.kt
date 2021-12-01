package com.lurking.cobra.blog.publication.service.api.model.dto

data class ReactionEvent(
    val publicationId: String,
    val reaction: String,
    val count: Int
)
