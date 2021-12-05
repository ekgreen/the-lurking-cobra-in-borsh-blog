package com.lurking.cobra.blog.farm.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticManager
import com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer.TextAnalyzer
import com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer.TextPublicationAnalyzer
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.HabrStatisticVisitor
import com.lurking.cobra.blog.farm.api.publication.rating.UrnPublicationRatingService
import com.lurking.cobra.blog.farm.impl.cycle.statistic.habr.HabrStatisticManager
import com.lurking.cobra.blog.farm.impl.cycle.statistic.habr.OkHabrStatisticVisitor
import com.lurking.cobra.blog.farm.impl.handler.rating.HabrPublicationRatingService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.lang.Nullable

@Configuration
class HabrConfiguration {

    @Bean
    fun habrStatisticManager(habrStatisticVisitor: HabrStatisticVisitor, @Nullable @TextAnalyzer analyzers: List<TextPublicationAnalyzer>?): PublicationStatisticManager {
        return HabrStatisticManager(habrStatisticVisitor, analyzers?: ArrayList())
    }

    @Bean
    fun habrStatisticVisitor(httpClient: OkHttpClient, mapper: ObjectMapper): HabrStatisticVisitor {
        return OkHabrStatisticVisitor(httpClient,mapper)
    }

    @Bean
    fun habrPublicationRatingService(): UrnPublicationRatingService{
        return HabrPublicationRatingService()
    }
}