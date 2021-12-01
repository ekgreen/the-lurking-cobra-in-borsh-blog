package com.lurking.cobra.blog.farm.impl.cycle.selection

import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy
import com.lurking.cobra.blog.farm.api.cycle.selection.SelectionStrategy
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain

class FilteringSelectionStrategy(private val filters: List<SelectionFilter>) : SelectionStrategy {

    override fun selectPublicationStrategy(publication: Publication): PublicationStrategy {
        return AggregatedSelectionFilteringChain(Publish(), filters).invoke(publication)
    }

    private class AggregatedSelectionFilteringChain(private val origin: SelectionFilterChain,
                                                    private val filters: List<SelectionFilter>) : SelectionFilterChain {

        var pointer: Int = 0

        override fun invoke(publication: Publication): PublicationStrategy {
            if(pointer == filters.size)
                return origin.invoke(publication)
            else
                return filters[pointer++].invoke(publication, this)
        }

    }

    private class Publish : SelectionFilterChain {
        override fun invoke(publication: Publication): PublicationStrategy {
            return PublicationStrategy.PUBLISHING
        }
    }
}