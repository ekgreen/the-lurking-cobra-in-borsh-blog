package com.lurking.cobra.blog.publication.service.api.model.dto

import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface PublicationMapper {
    fun convertToDto(publicationEntity: PublicationEntity) : PublicationDto

    @InheritInverseConfiguration
    fun convertToModel(publicationDto: PublicationDto) : PublicationEntity
}