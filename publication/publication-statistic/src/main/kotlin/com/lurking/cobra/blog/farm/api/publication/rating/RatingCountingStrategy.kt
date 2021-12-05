package com.lurking.cobra.blog.farm.api.publication.rating

import com.lurking.cobra.blog.farm.api.publication.model.PublicationRating

interface RatingCountingStrategy {

    fun rating(publicationRatings: Set<PublicationRating>): Double
}