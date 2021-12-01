package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationReactionListener
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class PublicationReactionListenerImpl : PublicationReactionListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    // Enum
    @RabbitListener(queues = ["reaction-queue"])
    override fun processReactionQueue(event: ReactionEvent) {
        logger.info("Received message from reaction queue: $event");
    }
}

