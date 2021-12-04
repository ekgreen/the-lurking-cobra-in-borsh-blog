package com.lurking.cobra.blog.farm.impl.handler.model.mapper

import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import org.springframework.stereotype.Service

@Service
class ServicePublicationMapper : PublicationMapper {

    override fun convertEntryToModel(entry: PublicationDto): Publication {
        return Publication(
            id          = entry.id,
            urn         = entry.urn!!,
            uri         = entry.uri!!,
            rating      = entry.rating,
            statistic   = entry.statistic,
        )
    }

    override fun convertModelToEntry(model: Publication): PublicationDto {
        return PublicationDto(
            id          = model.id,
            urn         = model.urn,
            uri         = model.uri,
            rating      = model.rating,
            statistic   = model.statistic,
        )
    }
}