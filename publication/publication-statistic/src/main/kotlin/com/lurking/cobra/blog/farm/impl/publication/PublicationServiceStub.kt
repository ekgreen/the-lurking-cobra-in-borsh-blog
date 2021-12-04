package com.lurking.cobra.blog.farm.impl.publication

import com.lurking.cobra.blog.farm.PublicationStatisticApplication
import com.lurking.cobra.blog.farm.api.publication.PublicationApi
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
@Profile(PublicationStatisticApplication.DEV_PROFILE)
class PublicationServiceStub(private val store: MutableMap<String,Publication> = ConcurrentHashMap()) : PublicationApi {

    override fun findById(id: String): Publication {
        return store[id]!!
    }

    override fun savePublication(publication: Publication): Publication {
        if(publication.id == null)
            publication.id = UUID.randomUUID().toString()

        store[publication.id!!] = publication

        return publication
    }

}