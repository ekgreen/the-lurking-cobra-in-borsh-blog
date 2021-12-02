package com.lurking.cobra.blog.publication.service.impl.configuration

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Конфигурация очередей rabbitmq
 */
@Configuration
class AmqpConfiguration {
    companion object {
        const val PUBLICATION_QUEUE  : String = "publication_queue"
        const val REACTION_QUEUE     : String = "reaction_queue"
    }
    /** Очередь - реакции к статье */
    @Bean
    fun reactionQueue(): Queue {
        return Queue(REACTION_QUEUE)
    }

    /** Очередь - уведомление о публикации статьи */
    @Bean
    fun publicationQueue(): Queue {
        return Queue(PUBLICATION_QUEUE)
    }
}