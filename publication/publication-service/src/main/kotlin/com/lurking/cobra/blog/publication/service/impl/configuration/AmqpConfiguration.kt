package com.lurking.cobra.blog.publication.service.impl.configuration

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Конфигурация очередей rabbitmq
 */
@Configuration
class AmqpConfiguration {
    /** Очередь - реакции к статье */
    @Bean
    fun reactionQueue(): Queue {
        return Queue("reaction-queue")
    }

    /** Очередь - уведомление о публикации статьи */
    @Bean
    fun publicationQueue(): Queue {
        return Queue("publication-queue")
    }
}