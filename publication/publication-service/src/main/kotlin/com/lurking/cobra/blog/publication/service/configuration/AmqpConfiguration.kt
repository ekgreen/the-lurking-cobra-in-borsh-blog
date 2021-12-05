package com.lurking.cobra.blog.publication.service.configuration

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Конфигурация очередей rabbitmq
 */
@Configuration
class AmqpConfiguration {
    companion object {
        const val PUBLICATION_SERVICE_EXCHANGER : String = "publication_exchange"
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

    @Bean
    fun publicationExchanger(): Exchange {
        return TopicExchange(PUBLICATION_SERVICE_EXCHANGER, true, false)
    }

    @Bean
    fun reactionQueueBinding(): Binding {
        return BindingBuilder
            .bind(reactionQueue())
            .to(publicationExchanger())
            .with("publication.*.reaction.*")
            .noargs()
    }

    @Bean
    fun publishQueueBinding(): Binding {
        return BindingBuilder
            .bind(publicationQueue())
            .to(publicationExchanger())
            .with("publication.*.published")
            .noargs()
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jsonMessageConverter())
        return factory
    }
}