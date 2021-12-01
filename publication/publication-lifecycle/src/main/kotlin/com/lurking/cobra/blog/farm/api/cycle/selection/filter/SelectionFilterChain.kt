package com.lurking.cobra.blog.farm.api.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.cycle.selection.SelectionStrategy
import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy

interface SelectionFilterChain {

    /**
     *
     */
    fun invoke(publication: Publication): PublicationStrategy
}