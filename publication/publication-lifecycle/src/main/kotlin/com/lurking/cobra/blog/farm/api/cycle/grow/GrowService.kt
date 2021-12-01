package com.lurking.cobra.blog.farm.api.cycle.grow

import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy

interface GrowService {

    fun attachPublication(publication: Publication, strategy: PublicationStrategy)
}