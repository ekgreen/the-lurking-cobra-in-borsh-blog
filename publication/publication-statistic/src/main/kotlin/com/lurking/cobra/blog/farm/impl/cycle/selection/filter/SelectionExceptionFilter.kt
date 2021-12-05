package com.lurking.cobra.blog.farm.impl.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter.Companion.EXCEPTION_FILTER
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy


class SelectionExceptionFilter : SelectionFilter {

    override fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy {
        try{
            return chain.invoke(publication)
        }catch (exception: Exception){
            // todo log
            return PublicationStrategy.FREEZE
        }
    }

    override fun getOrder(): Int = EXCEPTION_FILTER
}