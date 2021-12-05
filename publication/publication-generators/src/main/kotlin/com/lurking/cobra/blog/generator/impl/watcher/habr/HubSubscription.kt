package com.lurking.cobra.blog.generator.impl.watcher.habr

import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.HabrVisitor
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.TailVisitorRule
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.publication.Publication

/**
 * Итератор для подписанного хаба, предполагается последовательный вызов методов hasNext и next в неконкуретной среде
 */
class HubSubscription(private val hub: Hub, private val visitor: HabrVisitor, private val strategy: TailVisitorRule) : Iterator<Publication> {

    private var delegate: Iterator<Publication>? = null

    override fun hasNext(): Boolean {
        if(delegate == null)
            delegate = visitor.visitHub(hub, strategy)

        return delegate!!.hasNext()
    }

    override fun next(): Publication {
        return delegate!!.next()
    }
}