package com.lurking.cobra.blog.farm.api.publication.model.mapper

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationDto


interface PublicationMapper {

    /**
     *
     *
     */
    fun convertModelToEntry(model: Publication): PublicationDto
}