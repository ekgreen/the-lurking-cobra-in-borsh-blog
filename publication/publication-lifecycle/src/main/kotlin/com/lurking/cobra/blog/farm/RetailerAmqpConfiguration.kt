package com.lurking.cobra.blog.farm

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RetailerAmqpConfiguration {

    companion object{
        const val STATISTIC_QUEUE : String = "statistic_queue"
    }

    @Bean
    fun statisticQueue(): Queue {
        return Queue(STATISTIC_QUEUE, true, true, false)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter{
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