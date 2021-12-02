package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain
import com.lurking.cobra.blog.farm.api.publication.PublishingRatingService
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.ThresholdValueProducer
import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy

class ThresholdFilter(private val holdValueResolver: ThresholdValueProducer, private val  publishingRatingService: PublishingRatingService) : SelectionFilter {

    override fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy {
        val rate: Double =  publishingRatingCalculation(publication)

        if(rate <= holdValueResolver.freezeUpperBound(publication))
            return PublicationStrategy.FREEZE

        if(rate <= holdValueResolver.publishingLowBound(publication))
            return PublicationStrategy.GROW

        return chain.invoke(publication)
    }

    private fun publishingRatingCalculation(publication: Publication): Double {
        return publishingRatingService.publishingRate(publication)
    }
}