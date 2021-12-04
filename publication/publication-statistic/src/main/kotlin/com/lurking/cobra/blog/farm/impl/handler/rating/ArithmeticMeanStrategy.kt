package com.lurking.cobra.blog.farm.impl.handler.rating

import com.lurking.cobra.blog.farm.api.publication.model.PublicationRating
import com.lurking.cobra.blog.farm.api.publication.rating.RatingCountingStrategy

class ArithmeticMeanStrategy: RatingCountingStrategy {

    override fun rating(publicationRatings: Set<PublicationRating>): Double {
        val ratings = publicationRatings.filter { it.rating != null }

        return ratings.sumOf { it.rating!! } / ratings.size // пока-что такой алгоритм
    }
}