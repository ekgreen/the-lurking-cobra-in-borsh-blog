package com.lurking.cobra.blog.publication.service.impl.service

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.service.PublicationRatingService

class PublicationRatingServiceImpl : PublicationRatingService{

    override fun calcActualRating(publication: Publication): Int {
        return 10
    }
}