package com.lurking.cobra.blog.bot.configuration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.goodboy.telegram.bot.http.api.client.token.ContextBasedTokenResolver
import com.goodboy.telegram.bot.http.api.client.token.TelegramApiTokenResolver
import com.goodboy.telegram.bot.spring.configuration.TelegramBotConfiguration
import com.lurking.cobra.blog.bot.ItBlogBotApplication.Companion.DEV_PROFILE
import com.lurking.cobra.blog.bot.ItBlogBotApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.bot.api.publication.PublicationApi
import com.lurking.cobra.blog.bot.api.publication.PublisherService
import com.lurking.cobra.blog.bot.impl.publication.PublicationServiceImpl
import com.lurking.cobra.blog.bot.impl.publication.PublisherServiceImpl
import com.lurking.cobra.blog.bot.impl.publication.PublisherServiceStub
import okhttp3.OkHttpClient
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Configuration
class ItBlogBotConfiguration {

    companion object{
        const val PUBLICATION_SERVICE_EXCHANGER : String = "publication_exchange"
    }

    @Bean
    @Profile(PROD_PROFILE)
    fun publisherServiceImpl(template: RabbitTemplate, publishedApi: PublicationApi) : PublisherService {
        return PublisherServiceImpl(publishedApi, template)
    }

    @Bean
    @Profile(PROD_PROFILE)
    fun publicationServiceImpl(client: OkHttpClient, objectMapper: ObjectMapper) : PublicationApi {
        return PublicationServiceImpl(client, objectMapper)
    }

    @Bean
    @Profile(DEV_PROFILE)
    fun publisherServiceStub(): PublisherService {
        return PublisherServiceStub()
    }

    @Bean
    fun singleBotTokenResolver(configuration: TelegramBotConfiguration): TelegramApiTokenResolver {
        val token: String = configuration.bots.find { it.name.equals("ItBlog") }!!.token
        return TelegramApiTokenResolver { token }
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

    @Bean
    fun publicationStatisticObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .enable(
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
                DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,
                DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS
            )
            .enable(
                JsonParser.Feature.ALLOW_SINGLE_QUOTES
            )
            .disable(
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS
            )
            .disable(
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
            )
            .registerModules(KotlinModule.Builder().build(), JavaTimeModule(), Jdk8Module())
    }

}