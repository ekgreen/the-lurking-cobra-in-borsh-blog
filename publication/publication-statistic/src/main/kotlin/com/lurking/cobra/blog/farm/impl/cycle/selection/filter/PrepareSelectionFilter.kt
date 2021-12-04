package com.lurking.cobra.blog.farm.impl.cycle.selection.filter

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter.Companion.PREPARE_FILTER
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.VoterStrategy
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.impl.exception.PublicationDeniedException

class PrepareSelectionFilter(private val strategy: VoterStrategy) : SelectionFilter {

    override fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy {
        try {
            // 1. Голосование за допуск публикации к рассмотрению
            strategy.makeDecision(publication)


        }catch (denied: PublicationDeniedException){
            // todo log
            return PublicationStrategy.FREEZE
        }

        return chain.invoke(publication)
    }

    override fun getOrder(): Int = PREPARE_FILTER
}