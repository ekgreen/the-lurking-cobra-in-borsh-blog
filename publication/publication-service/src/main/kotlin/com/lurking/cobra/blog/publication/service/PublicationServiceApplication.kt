package com.lurking.cobra.blog.publication.service

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class PublicationServiceApplication{

    @Bean
    fun publicationObjectMapper(): ObjectMapper {
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

fun main(args: Array<String>) {
    runApplication<PublicationServiceApplication>(*args)
}
