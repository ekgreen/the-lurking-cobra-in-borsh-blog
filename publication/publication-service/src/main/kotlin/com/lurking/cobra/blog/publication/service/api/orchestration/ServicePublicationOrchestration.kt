package com.lurking.cobra.blog.publication.service.api.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import java.time.LocalDateTime

/**
 * Интерфейс, представляющий методы для работы с бизнес-логикой сервиса публикации
 */
interface ServicePublicationOrchestration {

    fun findPublicationById(id: String): Publication

    fun createPublication(model: Publication): Publication

    fun savePublication(model: Publication): Publication

    fun findMostActualPublications(count: Int): List<Publication>

    fun publicationEvent(event: PublicationEvent)

    fun reactionEvent(event: ReactionEvent)

    fun findPublicationsByTags(tags: Set<String>, count: Int): List<Publication>

    fun findMostPopularPublications(from: LocalDateTime, to: LocalDateTime): List<Publication>
}