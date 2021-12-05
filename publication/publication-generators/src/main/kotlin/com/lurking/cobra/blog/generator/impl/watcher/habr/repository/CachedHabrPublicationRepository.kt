package com.lurking.cobra.blog.generator.impl.watcher.habr.repository

import com.google.common.cache.Cache
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import com.lurking.cobra.blog.generator.api.watcher.habr.repository.HabrPublicationRepository
import java.time.LocalDateTime

class CachedHabrPublicationRepository(private val delegate: HabrPublicationRepository, private val cache: Cache<Hub, LocalDateTime>): HabrPublicationRepository {

    override fun getLastPublicationTimestampBySubscriptionNameAndType(subscriptionName: String, subscriptionType: HubType): LocalDateTime? {
        val key = createKey(subscriptionName, subscriptionType)

        return cache.get(key) { delegate.getLastPublicationTimestampBySubscriptionNameAndType(subscriptionName, subscriptionType)?: LocalDateTime.of(1970, 1, 1, 0, 0) }
    }

    override fun save(publication: Publication) {
        val key = publication.hub

        delegate.save(publication)

        val timestamp: LocalDateTime? =
            getLastPublicationTimestampBySubscriptionNameAndType(publication.hub.name, publication.hub.type)

        if(timestamp == null || publication.timestamp.isAfter(timestamp))
            cache.put(key, publication.timestamp)
    }

    override fun commit(publication: Publication) {
        delegate.commit(publication)
    }

    private fun createKey(subscriptionName: String, subscriptionType: HubType) : Hub {
        return Hub(subscriptionName,subscriptionType)
    }
}