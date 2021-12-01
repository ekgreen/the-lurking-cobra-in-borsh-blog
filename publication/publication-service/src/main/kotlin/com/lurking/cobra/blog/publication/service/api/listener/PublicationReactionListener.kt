package com.lurking.cobra.blog.publication.service.api.listener

import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent

/**
 * Интерфейс listener, который обрабатывает события из очереди реакций rabbitmq
 */
interface PublicationReactionListener {

    fun processReactionQueue(event: ReactionEvent)
}