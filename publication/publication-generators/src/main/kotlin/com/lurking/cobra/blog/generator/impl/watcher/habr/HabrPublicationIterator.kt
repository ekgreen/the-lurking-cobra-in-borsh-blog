package com.lurking.cobra.blog.generator.impl.watcher.habr

import com.lurking.cobra.blog.generator.api.publication.PublicationProducer
import com.lurking.cobra.blog.generator.api.publication.Publication

class HabrPublicationIterator(
    private val subscriptions: List<HubSubscription>,
    private val block: (() -> Publication) -> PublicationProducer
) : Iterator<PublicationProducer> {

    private var pointer: Int = 0

    override fun hasNext(): Boolean {
        var hasNext = false

        while (pointer < subscriptions.size) {
            if(subscriptions[pointer].hasNext()) {
                hasNext = true; break
            } else
                pointer += 1;
        }

        return hasNext
    }

    override fun next(): PublicationProducer {
        val it: Int = pointer
        return block { subscriptions[it].next() }
    }
}