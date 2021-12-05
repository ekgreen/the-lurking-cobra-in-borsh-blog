package com.lurking.cobra.blog.generator.impl.publication

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import mu.KLogging

class LoggingPublicationPublisher: PublicationPublisher {

    override fun publish(publication: Publication) {
            logger.info { "Generator publisher [log, exchange = LOG] success send publication { urn = ${publication.urn}, uri = ${publication.uri} } in log" }
    }

    companion object: KLogging()
}