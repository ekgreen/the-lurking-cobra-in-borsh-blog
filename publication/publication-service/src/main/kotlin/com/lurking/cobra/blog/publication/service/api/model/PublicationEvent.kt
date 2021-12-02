package com.lurking.cobra.blog.publication.service.api.model

/**
 * Класс события - публикация статьи ботом
 */
data class PublicationEvent (
    /** Id публикации, к которой относится реакция */
    val publicationId: String,

    val count: Int
)