package com.lurking.cobra.blog.generator.impl.publication

import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.configuration.GeneratorAmqpConfiguration.Companion.STATISTIC_EXCHANGER
import mu.KLogging
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate

class AmqpPublicationPublisher(private val template: RabbitTemplate, private val mapper: PublicationMapper): PublicationPublisher {

    override fun publish(publication: Publication) {
        try {
            template.convertAndSend(STATISTIC_EXCHANGER, "statistic.pushPublication.${publication.urn}", mapper.convertModelToEntry(publication)) { message ->
                message.messageProperties.contentType     = MessageProperties.CONTENT_TYPE_JSON
                message.messageProperties.contentEncoding = "UTF-8"

                return@convertAndSend message
            }
        }catch (any: Exception){
            logger.warn(any) { "Generator publisher [log, exchange = $STATISTIC_EXCHANGER] failed to send publication { urn = ${publication.urn}, uri = ${publication.uri} } in $STATISTIC_EXCHANGER" }
        }
    }

    companion object: KLogging()
}