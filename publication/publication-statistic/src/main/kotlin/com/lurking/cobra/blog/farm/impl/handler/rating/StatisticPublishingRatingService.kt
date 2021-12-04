package com.lurking.cobra.blog.farm.impl.handler.rating

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationRating
import com.lurking.cobra.blog.farm.api.publication.model.RatingType
import com.lurking.cobra.blog.farm.api.publication.rating.*
import org.springframework.stereotype.Service

@Service
class StatisticPublishingRatingService(
    private val ratingCountingStrategy: RatingCountingStrategy,
    ratingServices: List<UrnPublicationRatingService>
) : PublishingRatingService {

    private val ratingServices: Map<String, PublicationRatingService>

    init {
        this.ratingServices = ratingServices.associateBy { it.supportedUrn() }
    }

    override fun publishingRate(publication: Publication): Double {
        val service: PublicationRatingService = ratingServices[publication.urn] ?: return SERVICE_NOT_FOUND_RATING

        return ratingCountingStrategy.rating(setOf(
            PublicationRating(RatingType.STATISTIC, service.publicationStatisticRating(publication)),
            PublicationRating(RatingType.REACTIONS, service.publicationReactionRating(publication))
        ))
    }

    companion object {
        const val SERVICE_NOT_FOUND_RATING: Double = 0.0
    }
}