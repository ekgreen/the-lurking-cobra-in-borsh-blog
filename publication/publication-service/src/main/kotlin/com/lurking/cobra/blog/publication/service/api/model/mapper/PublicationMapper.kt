package com.lurking.cobra.blog.publication.service.api.model.mapper

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PublicationMapper {
    fun convertEntityToModel(publicationEntity: PublicationEntity) : Publication

    fun convertModelToEntity(publication: Publication) : PublicationEntity

    fun convertModelToDto(publication: Publication) : PublicationDto

    fun convertDtoToModel(publicationDto: PublicationDto) : Publication
}