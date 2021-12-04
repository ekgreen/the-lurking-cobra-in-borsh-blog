package com.lurking.cobra.blog.farm.impl.publication

import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.farm.PublicationStatisticApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationApi
import com.lurking.cobra.blog.farm.api.publication.PublicationApi.Companion.ADD_BY_ID_PATH
import com.lurking.cobra.blog.farm.api.publication.PublicationApi.Companion.EDIT_BY_ID_PATH
import com.lurking.cobra.blog.farm.api.publication.PublicationApi.Companion.VIEW_BY_ID_PATH
import com.lurking.cobra.blog.farm.api.publication.model.mapper.PublicationMapper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile(PROD_PROFILE)
class PublicationServiceImpl(
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper,
    private val entryMapper: PublicationMapper
) : PublicationApi {

    @Value("\${publication.service.url}")
    private lateinit var serviceUrl: String

    val JSON_TYPE: MediaType = MediaType.get("application/json; charset=utf-8")

    override fun findById(id: String): Publication {
        client.newCall(Request.Builder().url(serviceUrl + VIEW_BY_ID_PATH + id).build()).execute().use {
            return it.body()?.string().let { objectMapper.readValue(it, Publication::class.java) }
        }
    }

    override fun savePublication(publication: Publication): Publication {
        return if (publication.id == null) createPublication(publication) else updatePublication(publication)
    }

    private fun createPublication(publication: Publication): Publication {
        return postPublication(ADD_BY_ID_PATH, publication)
    }

    private fun updatePublication(publication: Publication): Publication {
        return postPublication(EDIT_BY_ID_PATH + publication.id, publication)
    }

    private fun postPublication(path: String, publication: Publication): Publication {
        return client.newCall(Request.Builder()
            .url(serviceUrl + path)
            .post(RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(publication)))
            .build()).execute()
            .use { it.body()?.string().let { objectMapper.readValue(it, PublicationDto::class.java) } }
            .let { entryMapper.convertEntryToModel(it) }
    }
}