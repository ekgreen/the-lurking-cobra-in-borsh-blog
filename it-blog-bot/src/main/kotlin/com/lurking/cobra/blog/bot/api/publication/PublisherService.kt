package com.lurking.cobra.blog.bot.api.publication

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
}