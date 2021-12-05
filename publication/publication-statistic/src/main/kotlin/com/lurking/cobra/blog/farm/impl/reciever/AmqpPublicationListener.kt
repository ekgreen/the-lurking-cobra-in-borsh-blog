package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.configuration.StatisticAmqpConfiguration.Companion.STATISTIC_QUEUE
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.reciever.PublicationListener
import org.springframework.amqp.rabbit.annotation.RabbitListener

class AmqpPublicationListener(private val publicationHandler: PublicationHandler) : PublicationListener {

    @RabbitListener(queues = [STATISTIC_QUEUE])
    override fun processPublicationRequest(request: PublicationDto) {
        publicationHandler.handlePublication(request)
    }
}