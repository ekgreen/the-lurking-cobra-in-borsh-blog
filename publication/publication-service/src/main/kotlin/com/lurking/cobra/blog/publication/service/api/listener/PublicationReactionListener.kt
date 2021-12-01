package com.lurking.cobra.blog.publication.service.api.listener

interface PublicationReactionListener {
    fun processReactionQueue(message: String)
}