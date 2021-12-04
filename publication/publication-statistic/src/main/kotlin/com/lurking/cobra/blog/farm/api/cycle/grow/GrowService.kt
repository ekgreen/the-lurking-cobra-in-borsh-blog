package com.lurking.cobra.blog.farm.api.cycle.grow

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

interface GrowService {

    fun attachPublication(publication: Publication, strategy: PublicationStrategy)
}