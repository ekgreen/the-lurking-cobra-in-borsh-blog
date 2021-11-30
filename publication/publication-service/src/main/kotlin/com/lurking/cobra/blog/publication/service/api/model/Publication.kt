package com.lurking.cobra.blog.publication.service.api.model

import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity

// mapstruct
class PublicationMapperImp : PublicationMapper {
    override fun convertToDto(publicationEntity: PublicationEntity): PublicationDto {
        TODO("Not yet implemented")
    }

    override fun convertToModel(publicationDto: PublicationDto): PublicationEntity {
        TODO("Not yet implemented")
    }
}