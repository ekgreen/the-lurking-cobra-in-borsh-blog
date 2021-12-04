package com.lurking.cobra.blog.generator.configuration

import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import com.lurking.cobra.blog.generator.BlogGeneratorsApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.generator.impl.publication.AmqpPublicationPublisher
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Configuration
@Profile(PROD_PROFILE)
class GeneratorAmqpConfiguration {

    companion object{
        const val STATISTIC_QUEUE     : String = "statistic_queue"
        const val STATISTIC_EXCHANGER : String = "statistic_exchange"
    }

    @Bean
    fun amqpPublicationPublisher(template: RabbitTemplate, mapper: PublicationMapper) : AmqpPublicationPublisher {
        return AmqpPublicationPublisher(template, mapper)
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