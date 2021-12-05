package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter.Companion.THRESHOLD_FILTER
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilterChain
import com.lurking.cobra.blog.farm.api.publication.rating.PublishingRatingService
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.ThresholdValueProducer
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import mu.KLogging

class ThresholdFilter(private val holdValueResolver: ThresholdValueProducer) : SelectionFilter {

    override fun invoke(publication: Publication, chain: SelectionFilterChain): PublicationStrategy {
        val rate: Double =  publication.rating

        val freezeUpperBound = holdValueResolver.freezeUpperBound(publication)
        if(rate <= freezeUpperBound) {
            logger.warn { "Threshold filter [log, urn = ${publication.urn}, uri = ${publication.uri} ] publication " +
                    "rating {${publication.rating}} under threshold freeze upper bound [$freezeUpperBound]" }

            return PublicationStrategy.FREEZE
        }

        val publishingLowBound = holdValueResolver.publishingLowBound(publication)
        if(rate <= publishingLowBound) {
            logger.info { "Threshold filter [log, urn = ${publication.urn}, uri = ${publication.uri} ] publication " +
                    "rating {${publication.rating}} under threshold publishing low bound [$publishingLowBound]" }

            return PublicationStrategy.GROW
        }

        return chain.invoke(publication)
    }

    override fun getOrder(): Int = THRESHOLD_FILTER

    companion object: KLogging()
}