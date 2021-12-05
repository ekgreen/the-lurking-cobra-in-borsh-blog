package com.lurking.cobra.blog.bot.impl.publication

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.bot.api.publication.Publication
import com.lurking.cobra.blog.bot.api.publication.PublicationApi
import com.lurking.cobra.blog.bot.api.publication.PublicationApi.Companion.QUERY_SEARCH
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value

class PublicationServiceImpl(
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper
) : PublicationApi {

    @Value("\${publication.service.url}")
    private lateinit var serviceUrl: String

    override fun getActualPosts(count: Int): List<Publication> {
        client.newCall(Request.Builder().url(serviceUrl + QUERY_SEARCH + count).build()).execute().use {
            if(it.code() != 200) return listOf()
            return it.body()?.string().let { json -> objectMapper.readValue(json, object : TypeReference<List<Publication>>() {}) }
        }
    }
}