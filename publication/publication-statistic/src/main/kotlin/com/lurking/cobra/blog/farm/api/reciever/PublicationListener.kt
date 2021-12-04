package com.lurking.cobra.blog.farm.api.reciever

import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto

interface PublicationListener {

    /**
     *
     *
     */
    fun processPublicationRequest(request: PublicationDto)
}