package com.lurking.cobra.blog.publication.service.api.listener

interface PublicationReactionListener {
    fun receiveQuery(message: String)
}