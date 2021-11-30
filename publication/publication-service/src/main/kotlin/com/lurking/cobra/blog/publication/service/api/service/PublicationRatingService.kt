package com.lurking.cobra.blog.publication.service.api.service

import com.lurking.cobra.blog.publication.service.api.model.Publication
import org.springframework.stereotype.Service

@Service
interface PublicationRatingService {
    fun calcActualRating(publication: Publication): Double
}