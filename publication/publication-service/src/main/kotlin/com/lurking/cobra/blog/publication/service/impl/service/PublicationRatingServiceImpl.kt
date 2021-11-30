package com.lurking.cobra.blog.publication.service.impl.service

import com.lurking.cobra.blog.publication.service.api.service.PublicationRatingService
import com.lurking.cobra.blog.publication.service.impl.repository.PublicationRepositoryImpl

class PublicationRatingServiceImpl(val publicationRepositoryImpl: PublicationRepositoryImpl) : PublicationRatingService{
    override fun calcActualRating() {

    }
}