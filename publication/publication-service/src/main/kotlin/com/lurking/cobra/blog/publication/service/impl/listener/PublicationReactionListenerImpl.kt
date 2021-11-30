package com.lurking.cobra.blog.publication.service.impl.listener

import com.lurking.cobra.blog.publication.service.api.listener.PublicationReactionListener
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service


@Service
class PublicationReactionListenerImpl() : PublicationReactionListener {

    @RabbitListener(queues = ["queue_reaction"])
    override fun receiveQuery(message: String) {
        println("queue_reaction: $message")
    }
}