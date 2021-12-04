package com.lurking.cobra.blog.farm.api.publication.rating


interface UrnPublicationRatingService: PublicationRatingService {

    fun supportedUrn(): String
}