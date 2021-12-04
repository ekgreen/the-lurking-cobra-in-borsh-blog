package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import com.lurking.cobra.blog.publication.service.impl.configuration.AmqpConfiguration.Companion.PUBLICATION_QUEUE
import com.lurking.cobra.blog.publication.service.impl.configuration.AmqpConfiguration.Companion.REACTION_QUEUE
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.MessageProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class PublicationServiceAmqpController {
    @Autowired
    var template: AmqpTemplate? = null

    @RequestMapping("/publication-queue")
    @ResponseBody
    fun publicationQueue(): String {
        val request = PublicationEvent("61a8e99117cb4739ef830834", 1)
        template!!.convertAndSend(PUBLICATION_QUEUE, request) { message ->
            message.messageProperties.contentType     = MessageProperties.CONTENT_TYPE_JSON
            message.messageProperties.contentEncoding = "UTF-8"

            return@convertAndSend message
        }
        return "request send to publication queue"
    }

    @RequestMapping("/reaction-queue")
    @ResponseBody
    fun reactionQueue(): String {
        val request = ReactionEvent("61a8e99117cb4739ef830834", "like", 1)
        template!!.convertAndSend(REACTION_QUEUE, request)
        return "request send to reaction queue"
    }
}