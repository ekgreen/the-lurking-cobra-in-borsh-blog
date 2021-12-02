package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationPublishingListener
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.logging.Logger

/**
 * Сервис, реализующий listener для очереди с уведомлениями о публикации статьи
 */
@Service
class PublicationPublishingListenerImpl @Autowired constructor(
    val orchestration: ServicePublicationOrchestration) : PublicationPublishingListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    @RabbitListener(queues = ["publication-queue"])
    override fun processPublicationQueue(event: PublicationEvent) {
        logger.info("Received event from publication queue: $event")
        orchestration.publicationEvent(event)
    }
}