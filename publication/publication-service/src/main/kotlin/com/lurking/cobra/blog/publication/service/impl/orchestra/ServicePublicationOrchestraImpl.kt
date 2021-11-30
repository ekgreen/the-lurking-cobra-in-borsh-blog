package com.lurking.cobra.blog.publication.service.impl.orchestra

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.entity.Status
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestra.ServicePublicationOrchestra
import com.lurking.cobra.blog.publication.service.api.repository.PublicationRepository
import org.mapstruct.factory.Mappers

class ServicePublicationOrchestraImpl(private val publicationRepository: PublicationRepository) : ServicePublicationOrchestra {

    private val converter = Mappers.getMapper(PublicationMapper::class.java)

    override fun getPublicationById(id: String): Publication {
        val entity = publicationRepository.findById(id).orElseThrow()
        return converter.convertEntityToModel(entity)
    }

    override fun createPublication(publicationDto: PublicationDto): Publication {
        val model = converter.convertDtoToModel(publicationDto)
        val entity = converter.convertModelToEntity(model)

        publicationRepository.save(entity)

        return  model
    }

    override fun updatePublication(publicationDto: PublicationDto): Publication {
        return createPublication(publicationDto)
    }

    override fun getPublicationsForPost(): List<Publication> {
        // тут по хорошему нужен еще один класс, который будет перебирать статьи и выдавать масссив публикаций
        return listOf(
            Publication(
                id = "_id",
                uri = "/habr",
                title = "title",
                rating = 10.0,
                tags = mutableSetOf("tags"),
                key_words = mutableSetOf("Kotlin"),
                reactions = mutableMapOf("like" to 0),
                status = Status.PUBLICATION_READY
        ))
    }
}