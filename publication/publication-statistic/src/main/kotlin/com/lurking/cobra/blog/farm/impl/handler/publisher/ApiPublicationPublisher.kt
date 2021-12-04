package com.lurking.cobra.blog.farm.impl.handler.publisher

import com.lurking.cobra.blog.farm.api.publication.PublicationApi
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.api.publication.publisher.PublicationPublisher
import mu.KLogging

class ApiPublicationPublisher(private var api: PublicationApi): PublicationPublisher {

    companion object: KLogging()

    override fun publish(publication: Publication, strategy: PublicationStrategy) {
        logger.info { "Published [log, strategy = $strategy]: $publication" }

        api.savePublication(publication)
    }
}