package com.lurking.cobra.blog.publication.service.api.listener

interface PublicationPublishingListener {
    fun processPublicationQueue(message: String)
}