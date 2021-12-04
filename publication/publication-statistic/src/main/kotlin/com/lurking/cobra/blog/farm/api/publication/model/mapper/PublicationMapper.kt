package com.lurking.cobra.blog.farm.api.publication.model.mapper

import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface PublicationMapper {

    /**
     *
     *
     */
    fun convertEntryToModel(entry: PublicationDto): Publication

    /**
     *
     *
     */
    fun convertModelToEntry(model: Publication): PublicationDto
}