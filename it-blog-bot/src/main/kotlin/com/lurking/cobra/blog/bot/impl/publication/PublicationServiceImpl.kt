package com.lurking.cobra.blog.bot.impl.publication

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.bot.api.publication.Publication
import com.lurking.cobra.blog.bot.api.publication.PublicationApi
import com.lurking.cobra.blog.bot.api.publication.PublicationApi.Companion.FIND_SEARCH
import com.lurking.cobra.blog.bot.api.publication.PublicationApi.Companion.GET_DIGEST
import com.lurking.cobra.blog.bot.api.publication.PublicationApi.Companion.QUERY_SEARCH
import com.lurking.cobra.blog.bot.api.publication.PublicationQueryRequest
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Value

class PublicationServiceImpl(
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper
) : PublicationApi {

    @Value("\${publication.service.url}")
    private lateinit var serviceUrl: String

    private val JSON_TYPE: MediaType = MediaType.get("application/json; charset=utf-8")

    override fun getActualPosts(count: Int): List<Publication> {
        client.newCall(Request.Builder().url(serviceUrl + FIND_SEARCH + count).build()).execute().use {
            if (it.code() != 200) return listOf()
            return it.body()?.string()
                .let { json -> objectMapper.readValue(json, object : TypeReference<List<Publication>>() {}) }
        }
    }

    override fun findPostByTags(tags: Set<String>, count: Int): List<Publication> {
        client.newCall(
            Request.Builder()
                .url(serviceUrl + QUERY_SEARCH)
                .post(RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(PublicationQueryRequest(tags, count))))
                .build()
        ).execute().use {
            if (it.code() != 200) return listOf()
            return it.body()?.string()
                .let { json -> objectMapper.readValue(json, object : TypeReference<List<Publication>>() {}) }
        }
    }

    override fun getDigest(count: Int): List<Publication> {
        client.newCall(Request.Builder().url(serviceUrl + GET_DIGEST + count).build()).execute().use {
            if (it.code() != 200) return listOf()
            return it.body()?.string()
                .let { json -> objectMapper.readValue(json, object : TypeReference<List<Publication>>() {}) }
        }
    }
}