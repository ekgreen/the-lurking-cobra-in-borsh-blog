package com.lurking.cobra.blog.publication.service.api.model.dto

/**
 * Класс события - реакция
 */
data class ReactionEvent(
    /** Id публикации, к которой относится реакция */
    val publicationId: String,

    /** Название реакции */
    val reaction: String,

    /** Число для добавления в бд, так как на каждую реакцию создается новый объект события,
     * то это поле будет иметь значение 1 */
    val count: Int
)
