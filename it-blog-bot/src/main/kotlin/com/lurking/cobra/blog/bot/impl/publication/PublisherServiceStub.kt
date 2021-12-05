package com.lurking.cobra.blog.bot.impl.publication

import com.lurking.cobra.blog.bot.api.publication.Publication
import com.lurking.cobra.blog.bot.api.publication.PublisherService
import com.lurking.cobra.blog.bot.api.publication.ReactionEvent
import mu.KLogging


class PublisherServiceStub() : PublisherService {

    override fun publicationForPost(): Publication {
        return Publication(
            id = "588282",
            uri = "https://habr.com/ru/post/588282/",
            title = "CSS и XPath для QA: чтобы разобраться с локаторами, нужно всего лишь…",
            tags = setOf("Java", "Kotlin")
        )
    }

    override fun publicationPosted(publication: Publication) {
    }

    override fun publicationReaction(eventBatch: ReactionEvent) {

    }

    companion object : KLogging()
}