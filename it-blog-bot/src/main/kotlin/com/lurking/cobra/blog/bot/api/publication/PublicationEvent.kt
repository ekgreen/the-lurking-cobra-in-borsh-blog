package com.lurking.cobra.blog.bot.api.publication

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Класс события - публикация статьи ботом
 */
data class PublicationEvent (
    /** Id публикации, к которой относится реакция */
    @JsonProperty("publicationId")
    val publicationId: String,

    @JsonProperty("count")
    val count: Int
)