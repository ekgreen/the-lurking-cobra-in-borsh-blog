package com.lurking.cobra.blog.bot.impl.publication

import com.lurking.cobra.blog.bot.api.publication.*
import com.lurking.cobra.blog.bot.configuration.ItBlogBotConfiguration.Companion.GENERATOR_SERVICE_EXCHANGER
import com.lurking.cobra.blog.bot.configuration.ItBlogBotConfiguration.Companion.PUBLICATION_SERVICE_EXCHANGER
import com.lurking.cobra.blog.bot.api.flow.recommendation.Recommendation
import mu.KLogging
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate


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

    override fun publishRecommendation(recommendation: Recommendation) {
        try {
            template.convertAndSend(GENERATOR_SERVICE_EXCHANGER, "publication.recommendation.${recommendation.source!!.name.lowercase()}", recommendation) { message ->
                message.messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON
                message.messageProperties.contentEncoding = "UTF-8"

                return@convertAndSend message
            }
            logger.info { "Bot publisher [log, exchange = $GENERATOR_SERVICE_EXCHANGER] success send publication recommendation { urn = ${recommendation.source}, uri = ${recommendation.link} } in $GENERATOR_SERVICE_EXCHANGER" }
        } catch (any: Exception) {
            logger.warn(any) { "Bot publisher [log, exchange = $GENERATOR_SERVICE_EXCHANGER] failed to send publication recommendation { urn = ${recommendation.source}, uri = ${recommendation.link} } } in $GENERATOR_SERVICE_EXCHANGER" }
        }
    }

    companion object : KLogging()
}