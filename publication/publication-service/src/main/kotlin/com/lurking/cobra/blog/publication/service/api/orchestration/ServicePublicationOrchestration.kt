package com.lurking.cobra.blog.publication.service.api.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent

// todo javadocs
interface ServicePublicationOrchestration {

    fun findPublicationById(id: String): Publication

    fun createPublication(model: Publication): Publication

    fun savePublication(model: Publication): Publication

    fun findMostActualPublications(count: Int): List<Publication>

    fun publicationEvent(event: PublicationEvent)

    fun reactionEvent(event: ReactionEvent)
}