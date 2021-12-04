package com.lurking.cobra.blog.farm.api.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

interface SelectionFilterChain {

    /**
     *
     */
    fun invoke(publication: Publication): PublicationStrategy
}