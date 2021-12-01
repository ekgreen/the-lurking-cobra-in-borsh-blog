package com.lurking.cobra.blog.publication.service.impl.configuration

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfiguration {
    @Bean
    fun reactionQueue(): Queue {
        return Queue("reaction-queue")
    }

    @Bean
    fun publicationQueue(): Queue {
        return Queue("publication-queue")
    }
}