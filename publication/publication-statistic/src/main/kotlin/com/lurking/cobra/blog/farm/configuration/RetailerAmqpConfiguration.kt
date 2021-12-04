package com.lurking.cobra.blog.farm.configuration

import com.lurking.cobra.blog.farm.PublicationStatisticApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.reciever.PublicationListener
import com.lurking.cobra.blog.farm.impl.reciever.AmqpPublicationListener
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Configuration
@Profile(PROD_PROFILE)
class RetailerAmqpConfiguration {

    companion object{
        const val STATISTIC_QUEUE : String = "statistic_queue"
    }

    @Bean
    fun statisticQueue(): Queue {
        return Queue(STATISTIC_QUEUE, true, true, false)
    }

    @Bean
    fun amqpPublicationListener(publicationHandler: PublicationHandler): PublicationListener {
        return AmqpPublicationListener(publicationHandler)
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