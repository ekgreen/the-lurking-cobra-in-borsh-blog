package com.lurking.cobra.blog.farm.api.publication

import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.farm.api.publication.PublicationApi.Companion.VIEW_BY_ID_PATH
import okhttp3.OkHttpClient
import okhttp3.Request

class PublicationServiceImpl(private val client: OkHttpClient, private val mapper: ObjectMapper) : PublicationApi {

    override fun findById(id: String): Publication {
        client.newCall(Request.Builder().url(VIEW_BY_ID_PATH + id).build()).execute().use {
            return it.body()?.string().let { mapper.readValue(it, Publication::class.java) }
        }
    }

    override fun savePublication(publication: Publication): String {
        TODO("Not yet implemented")
    }
}