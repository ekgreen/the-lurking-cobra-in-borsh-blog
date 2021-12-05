package com.lurking.cobra.blog.farm.impl.cycle.statistic.habr

import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.HabrStatisticVisitor
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article.Article
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHabrStatisticVisitor(private val client: OkHttpClient, private val objectMapper: ObjectMapper): HabrStatisticVisitor {

    override fun publicationStatistic(articleId: String): Article {
        client.newCall(Request.Builder().url("https://habr.com/kek/v2/articles/$articleId").build()).execute().use {
            return it.body()?.string().let { objectMapper.readValue(it, Article::class.java) }
        }
    }
}