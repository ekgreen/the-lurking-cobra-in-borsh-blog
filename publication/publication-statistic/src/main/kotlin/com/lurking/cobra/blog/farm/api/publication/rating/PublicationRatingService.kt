package com.lurking.cobra.blog.farm.api.publication.rating

import com.lurking.cobra.blog.farm.api.publication.model.Publication


interface PublicationRatingService {

    /**
     *
     *
     */

    fun publicationStatisticRating(publication: Publication): Double?

    /**
     *
     *
     */
    fun publicationReactionRating(publication: Publication): Double?
}