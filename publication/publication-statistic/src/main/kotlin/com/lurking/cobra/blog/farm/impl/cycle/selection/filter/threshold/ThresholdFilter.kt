package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter.Companion.THRESHOLD_FILTER
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain
import com.lurking.cobra.blog.farm.api.publication.rating.PublishingRatingService
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.ThresholdValueProducer
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

class ThresholdFilter(private val holdValueResolver: ThresholdValueProducer) : SelectionFilter {

    override fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy {
        val rate: Double =  publication.rating

        if(rate <= holdValueResolver.freezeUpperBound(publication))
            return PublicationStrategy.FREEZE

        if(rate <= holdValueResolver.publishingLowBound(publication))
            return PublicationStrategy.GROW

        return chain.invoke(publication)
    }

    override fun getOrder(): Int = THRESHOLD_FILTER
}