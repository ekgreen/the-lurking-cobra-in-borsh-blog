package com.lurking.cobra.blog.farm.impl.handler.model.mapper

import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import org.springframework.stereotype.Service

@Service
class ServicePublicationMapper : PublicationMapper {

    override fun convertEntryToModel(entry: PublicationDto): Publication {
        return Publication(
            id                  = entry.id,
            title               = entry.title,
            urn                 = entry.urn!!,
            uri                 = entry.uri!!,
            rating              = entry.rating,
            tags                = entry.tags,
            keywords            = entry.keywords,
            strategy            = entry.strategy,
            statistic           = entry.statistic,
            reactions           = entry.reactions,
            publicationsCount   = entry.publicationsCount,
            lastPublicationDate = entry.lastPublicationDate,
        )
    }

    override fun convertModelToEntry(model: Publication): PublicationDto {
        return PublicationDto(
            id                  = model.id,
            title               = model.title,
            urn                 = model.urn,
            uri                 = model.uri,
            rating              = model.rating,
            tags                = model.tags,
            keywords            = model.keywords,
            strategy            = model.strategy,
            statistic           = model.statistic,
            reactions           = model.reactions,
            publicationsCount   = model.publicationsCount,
            lastPublicationDate = model.lastPublicationDate,
        )
    }
}