package com.lurking.cobra.blog.generator.impl.publication.model.mapper

import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationDto
import org.springframework.stereotype.Service

@Service
class ServicePublicationMapper : PublicationMapper {

    override fun convertModelToEntry(model: Publication): PublicationDto {
        return PublicationDto(
            urn         = model.urn,
            uri         = model.uri,
        )
    }
}