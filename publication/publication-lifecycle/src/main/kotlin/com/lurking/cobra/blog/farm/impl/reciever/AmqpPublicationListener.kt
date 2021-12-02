package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.RetailerAmqpConfiguration.Companion.STATISTIC_QUEUE
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.handler.PublicationRequest
import com.lurking.cobra.blog.farm.api.reciever.PublicationListener
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class AmqpPublicationListener(private val publicationHandler: PublicationHandler) : PublicationListener {

    @RabbitListener(queues = [STATISTIC_QUEUE])
    override fun processPublicationRequest(request: PublicationRequest) {
        publicationHandler.handlePublication(request)
    }
}