package com.lurking.cobra.blog.generator.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.lurking.cobra.blog.generator.api.Generator
import com.lurking.cobra.blog.generator.api.GeneratorLog
import com.lurking.cobra.blog.generator.api.watcher.habr.HabrConfiguration
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.repository.HabrPublicationRepository
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.HabrVisitor
import com.lurking.cobra.blog.generator.impl.watcher.habr.HabrWatcher
import com.lurking.cobra.blog.generator.impl.watcher.habr.repository.CachedHabrPublicationRepository
import com.lurking.cobra.blog.generator.impl.watcher.habr.repository.JdbcHabrPublicationRepository
import com.lurking.cobra.blog.generator.impl.watcher.habr.visitor.HabrVisitorImpl
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Configuration
class HabrWatcherConfiguration {

    @Bean
    fun habrWatcher(configuration: HabrConfiguration,
                    visitors: List<HabrVisitor>,
                    repository: HabrPublicationRepository
    ) : Generator {
        return HabrWatcher(configuration, visitors, repository)
    }

    @Bean
    fun habrVisitorImpl(client: OkHttpClient, objectMapper: ObjectMapper): HabrVisitor {
        return HabrVisitorImpl(client, objectMapper)
    }

    @Bean
    fun jdbcHabrPublicationRepository(namedTemplate: NamedParameterJdbcTemplate, generatorLog: GeneratorLog): HabrPublicationRepository {
        return JdbcHabrPublicationRepository(namedTemplate, generatorLog)
    }

    @Bean
    @Primary
    fun cachedHabrPublicationRepository(delegate: HabrPublicationRepository, cache: Cache<Hub, LocalDateTime>): HabrPublicationRepository {
        return CachedHabrPublicationRepository(delegate, cache)
    }

    @Bean
    fun cachedHabrPublicationRepositoryLoadingCache(): Cache<Hub, LocalDateTime> {
        return CacheBuilder
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build()
    }
}