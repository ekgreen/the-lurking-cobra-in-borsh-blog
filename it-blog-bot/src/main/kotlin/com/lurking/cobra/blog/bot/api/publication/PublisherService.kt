package com.lurking.cobra.blog.bot.api.publication

import com.lurking.cobra.blog.bot.api.flow.recommendation.Recommendation

interface PublisherService {

    /**
     *
     *
     */
    fun publicationForPost(): Publication?

    /**
     *
     *
     */
    fun publicationPosted(publication: Publication)

    /**
     *
     *
     */
    fun publicationReaction(eventBatch: ReactionEvent)

    /**
     *
     *
     */
    fun publishRecommendation(recommendation: Recommendation)
}