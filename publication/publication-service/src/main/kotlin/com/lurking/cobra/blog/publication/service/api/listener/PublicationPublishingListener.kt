package com.lurking.cobra.blog.publication.service.api.listener

interface PublicationPublishingListener {

    // todo javadoc
    fun processPublicationQueue(message: String)
}