package com.lurking.cobra.blog.publication.service.impl.listener

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@EnableRabbit
@Component
class PublicationReactionListenerImpl {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    @RabbitListener(queues = ["reaction-queue"])
    fun processReactionQueue(message: String?) {
        logger.info("Received message from reaction queue: $message");
    }

    @RabbitListener(queues = ["publication-queue"])
    fun processPublicationQueue(message: String?) {
        logger.info("Received message from publication queue: $message");
    }
}

