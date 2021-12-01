package com.lurking.cobra.blog.publication.service.api.listener

interface PublicationReactionListener {

    // todo javadoc
    fun processReactionQueue(message: String)
}