package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationReactionListener
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@EnableRabbit
@Component
class PublicationReactionListenerImpl : PublicationReactionListener {
    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    @RabbitListener(queues = ["reaction-queue"])
    override fun processReactionQueue(message: String) {
        logger.info("Received message from reaction queue: $message");
    }
}

