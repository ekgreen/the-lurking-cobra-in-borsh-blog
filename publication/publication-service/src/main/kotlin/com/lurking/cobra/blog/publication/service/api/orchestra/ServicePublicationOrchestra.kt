package com.lurking.cobra.blog.publication.service.api.orchestra

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto

interface ServicePublicationOrchestra {
    fun getPublicationById(id: String): Publication

    fun createPublication(publicationDto: PublicationDto): Publication

    fun updatePublication(publicationDto: PublicationDto): Publication

    fun getPublicationsForPost(): List<Publication>
}