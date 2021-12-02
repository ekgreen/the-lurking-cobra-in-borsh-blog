package com.lurking.cobra.blog.farm.api.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy

interface SelectionFilter{

    /**
     *
     *
     */
    fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy
}