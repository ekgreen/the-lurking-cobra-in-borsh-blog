package com.lurking.cobra.blog.farm.impl.handler.publisher

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.api.publication.publisher.PublicationPublisher
import mu.KLogging
import java.util.*

class LoggerPublicationPublisher: PublicationPublisher {

    companion object: KLogging()

    override fun publish(publication: Publication, strategy: PublicationStrategy) {
        if(publication.id == null)
            publication.id  = UUID.randomUUID().toString()

        logger.info { "Published [log, strategy = $strategy]: $publication" }
    }
}