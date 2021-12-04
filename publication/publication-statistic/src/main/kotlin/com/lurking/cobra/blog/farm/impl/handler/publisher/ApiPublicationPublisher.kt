package com.lurking.cobra.blog.farm.impl.handler.publisher

import com.lurking.cobra.blog.farm.api.publication.PublicationApi
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.api.publication.publisher.PublicationPublisher

class ApiPublicationPublisher(private var api: PublicationApi): PublicationPublisher {

    override fun publish(publication: Publication, strategy: PublicationStrategy) {
        api.savePublication(publication)
    }
}