package com.lurking.cobra.blog.farm.api.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import org.springframework.core.Ordered

interface SelectionFilter : Ordered{

    companion object {
        const val EXCEPTION_FILTER = 0
        const val PREPARE_FILTER = 10
        const val THRESHOLD_FILTER = 100
    }

    /**
     *
     *
     */
    fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy
}