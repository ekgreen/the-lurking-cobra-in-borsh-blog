package com.lurking.cobra.blog.generator.impl.watcher.habr

import com.lurking.cobra.blog.generator.api.publication.PublicationProducer
import com.lurking.cobra.blog.generator.api.publication.Publication

class HabrPublicationIterator(
    private val subscriptions: List<HubSubscription>,
    private val block: (Publication) -> PublicationProducer
) : Iterator<PublicationProducer> {

    private var pointer: Int = 0

    override fun hasNext(): Boolean {
        var hasNext = false

        while (pointer < subscriptions.size && !subscriptions[pointer].hasNext())
            pointer += 1; hasNext = true // todo глянуть во что компилируется

        return hasNext
    }

    override fun next(): PublicationProducer {
        return block(subscriptions[pointer].next())
    }
}