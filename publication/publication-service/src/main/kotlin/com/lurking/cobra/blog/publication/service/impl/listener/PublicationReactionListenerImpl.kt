package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationReactionListener
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.logging.Logger

/**
 * Сервис, реализующий listener для очереди полученных реакций на статью
 */
@Service
class PublicationReactionListenerImpl @Autowired constructor(
    val orchestration: ServicePublicationOrchestration) : PublicationReactionListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    @RabbitListener(queues = ["reaction-queue"])
    override fun processReactionQueue(event: ReactionEvent) {
        logger.info("Received event from reaction queue: $event")
        orchestration.reactionEvent(event)
    }
}

