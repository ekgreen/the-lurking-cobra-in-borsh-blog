package com.lurking.cobra.blog.publication.service.api.service

import org.springframework.stereotype.Service

@Service
interface PublicationRatingService {
    fun calcActualRating()
}