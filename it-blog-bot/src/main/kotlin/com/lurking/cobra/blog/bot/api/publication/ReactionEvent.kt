package com.lurking.cobra.blog.bot.api.publication

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Класс события - реакция
 */
data class ReactionEvent(
    /** Id публикации, к которой относится реакция */
    @JsonProperty("publicationId")
    val publicationId: String,

    /** Название реакции */
    @JsonProperty("reaction")
    val reaction: String,

    /** Число для добавления в бд, так как на каждую реакцию создается новый объект события,
     * то это поле будет иметь значение 1 */
    @JsonProperty("count")
    val count: Int
)
