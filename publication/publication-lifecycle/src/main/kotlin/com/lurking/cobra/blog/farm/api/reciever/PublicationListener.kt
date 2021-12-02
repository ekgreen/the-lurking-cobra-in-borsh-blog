package com.lurking.cobra.blog.farm.api.reciever

import com.lurking.cobra.blog.farm.api.handler.PublicationRequest

interface PublicationListener {

    /**
     *
     *
     */
    fun processPublicationRequest(request: PublicationRequest)
}