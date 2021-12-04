package com.lurking.cobra.blog.generator.configuration

import com.lurking.cobra.blog.generator.BlogGeneratorsApplication.Companion.DEV_PROFILE
import com.lurking.cobra.blog.generator.api.Generator
import com.lurking.cobra.blog.generator.api.GeneratorLog
import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.impl.JdbcGeneratorLog
import com.lurking.cobra.blog.generator.impl.PublicationGenerator
import com.lurking.cobra.blog.generator.impl.publication.LoggingPublicationPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class GeneratorConfiguration {

    @Bean
    fun publicationGenerator(generators: List<Generator>, publisher: PublicationPublisher) : PublicationGenerator {
        return PublicationGenerator(generators, publisher)
    }

    @Bean
    @Profile(DEV_PROFILE)
    fun loggingPublicationPublisher(): PublicationPublisher {
        return LoggingPublicationPublisher()
    }

    @Bean
    fun jdbcGeneratorLog(jdbcTemplate: NamedParameterJdbcTemplate): GeneratorLog{
        return JdbcGeneratorLog(jdbcTemplate)
    }
}