package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationPublishingListener
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PublicationPublishingListenerImpl : PublicationPublishingListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    // todo стринг убрать, сделать енам PUBLISHED
    @RabbitListener(queues = ["publication-queue"])
    override fun processPublicationQueue(message: String) {
        logger.info("Received message from publication queue: $message");
    }
}