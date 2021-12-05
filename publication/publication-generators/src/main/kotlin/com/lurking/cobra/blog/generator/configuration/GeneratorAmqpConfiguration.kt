package com.lurking.cobra.blog.generator.configuration

import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import com.lurking.cobra.blog.generator.BlogGeneratorsApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.api.watcher.habr.recommendation.HabrRecommendation
import com.lurking.cobra.blog.generator.impl.publication.AmqpPublicationPublisher
import com.lurking.cobra.blog.generator.impl.watcher.habr.recommendation.HabrRecommendationListener
import org.springframework.amqp.core.*
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
        const val GENERATOR_HABR_QUEUE  : String = "habr_queue"
        const val GENERATOR_EXCHANGER   : String = "generator_exchange"
        const val STATISTIC_EXCHANGER   : String = "statistic_exchange"
    }

    @Bean
    fun habrQueue(): Queue {
        return Queue(GENERATOR_HABR_QUEUE, true, true, false)
    }

    @Bean
    fun generatorExchanger(): Exchange {
        return TopicExchange(GENERATOR_EXCHANGER, true, false)
    }

    @Bean
    fun habrQueueBinding(): Binding {
        return BindingBuilder
            .bind(habrQueue())
            .to(generatorExchanger())
            .with("publication.recommendation.habr")
            .noargs()
    }

    @Bean
    fun amqpPublicationPublisher(template: RabbitTemplate, mapper: PublicationMapper) : AmqpPublicationPublisher {
        return AmqpPublicationPublisher(template, mapper)
    }

    @Bean
    fun habrRecommendationListener(publisher: PublicationPublisher): HabrRecommendation {
        return HabrRecommendationListener(publisher)
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