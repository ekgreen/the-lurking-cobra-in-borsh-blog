package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationPublishingListener
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@EnableRabbit
@Component
class PublicationPublishingListenerImpl : PublicationPublishingListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    @RabbitListener(queues = ["publication-queue"])
    override fun processPublicationQueue(message: String) {
        logger.info("Received message from publication queue: $message");
    }
}