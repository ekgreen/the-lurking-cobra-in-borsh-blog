package com.lurking.cobra.blog.generator.impl.watcher.habr.recommendation

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import com.lurking.cobra.blog.generator.api.watcher.habr.recommendation.HabrRecommendation
import com.lurking.cobra.blog.generator.api.watcher.habr.recommendation.Recommendation
import com.lurking.cobra.blog.generator.configuration.GeneratorAmqpConfiguration.Companion.GENERATOR_HABR_QUEUE
import com.lurking.cobra.blog.generator.impl.watcher.habr.HabrWatcher
import mu.KLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import java.time.LocalDateTime
import java.util.regex.Pattern

class HabrRecommendationListener(private val publisher: PublicationPublisher) : HabrRecommendation {

    private val uriPattern: Pattern = Pattern.compile("^https://habr.com/ru/post/[0-9]+/$")

    @RabbitListener(queues = [GENERATOR_HABR_QUEUE])
    override fun handleRecommendation(recommendation: Recommendation) {
        if(recommendation.link != null && uriPattern.matcher(recommendation.link!!).matches()){
            publisher.publish(Publication(HabrWatcher.SOURCE_NAME, recommendation.link!!, Hub("recommendation", HubType.AUTHOR), LocalDateTime.now()))
        }
    }

    companion object: KLogging()
}