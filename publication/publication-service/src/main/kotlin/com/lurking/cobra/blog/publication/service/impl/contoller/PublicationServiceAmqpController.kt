package com.lurking.cobra.blog.publication.service.impl.contoller

import org.springframework.amqp.core.AmqpTemplate
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
        template!!.convertAndSend("publication-queue", "hello from publication queue")
        return "message send to publication queue"
    }

    @RequestMapping("/reaction-queue")
    @ResponseBody
    fun reactionQueue(): String {
        template!!.convertAndSend("reaction-queue", "hello from reaction queue")
        return "message send to reaction queue"
    }
}