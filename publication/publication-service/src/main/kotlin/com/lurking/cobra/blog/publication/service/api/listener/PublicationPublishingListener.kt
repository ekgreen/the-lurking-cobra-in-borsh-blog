package com.lurking.cobra.blog.publication.service.api.listener

import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent

/**
 * Интерфейс listener, который обрабатывает события из очереди публикаций rabbitmq
 */
interface PublicationPublishingListener {

    fun processPublicationQueue(event: PublicationEvent)
}