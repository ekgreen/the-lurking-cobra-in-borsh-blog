package com.lurking.cobra.blog.bot.impl.publication

import com.lurking.cobra.blog.bot.ItBlogBotApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.bot.api.publication.*
import com.lurking.cobra.blog.bot.configuration.ItBlogBotConfiguration.Companion.PUBLICATION_SERVICE_EXCHANGER
import mu.KLogging
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service


class PublisherServiceImpl(
    private val apiService: PublicationApi,
    private val template: RabbitTemplate,
) : PublisherService {

    override fun publicationForPost(): Publication? {
        return apiService.getActualPosts(1).firstOrNull()
    }

    override fun publicationPosted(publication: Publication) {
        try {
            template.convertAndSend(
                PUBLICATION_SERVICE_EXCHANGER,
                "publication.${publication.id}.published",
                PublicationEvent(publication.id, 1)
            ) { message ->
                message.messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON
                message.messageProperties.contentEncoding = "UTF-8"

                return@convertAndSend message
            }
            logger.info { "Bot publisher [log, exchange = $PUBLICATION_SERVICE_EXCHANGER] success send publication publish notification { id = ${publication.id} } in $PUBLICATION_SERVICE_EXCHANGER" }
        } catch (any: Exception) {
            logger.warn(any) { "Bot publisher [log, exchange = $PUBLICATION_SERVICE_EXCHANGER] failed to send publication publish notification { id = ${publication.id} } in $PUBLICATION_SERVICE_EXCHANGER" }
        }
    }

    override fun publicationReaction(eventBatch: ReactionEvent) {
        try {
            template.convertAndSend(
                PUBLICATION_SERVICE_EXCHANGER,
                "publication.${eventBatch.publicationId}.reaction.${eventBatch.reaction}",
                eventBatch
            ) { message ->
                message.messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON
                message.messageProperties.contentEncoding = "UTF-8"

                return@convertAndSend message
            }
            logger.info { "Bot publisher [log, exchange = $PUBLICATION_SERVICE_EXCHANGER] success send publication reaction notification { id = ${eventBatch.publicationId}, reaction = ${eventBatch.reaction}, count = ${eventBatch.reaction} } in $PUBLICATION_SERVICE_EXCHANGER" }
        } catch (any: Exception) {
            logger.warn(any) { "Bot publisher [log, exchange = $PUBLICATION_SERVICE_EXCHANGER] failed to send publication reaction notification { id = ${eventBatch.publicationId}, reaction = ${eventBatch.reaction}, count = ${eventBatch.reaction} } in $PUBLICATION_SERVICE_EXCHANGER" }
        }
    }

    companion object : KLogging()
}