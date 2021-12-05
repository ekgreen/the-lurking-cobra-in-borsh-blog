package com.lurking.cobra.blog.generator.impl.watcher.habr.visitor

import com.fasterxml.jackson.databind.ObjectMapper
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.Article
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.HabrVisitor
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.TailVisitorRule
import com.lurking.cobra.blog.generator.impl.watcher.habr.HabrWatcher.Companion.SOURCE_NAME
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HabrVisitorImpl(private val client: OkHttpClient, private val mapper: ObjectMapper) : HabrVisitor {

    override fun visitHub(hub: Hub, rule: TailVisitorRule): Iterator<Publication> {
        return object : Iterator<Publication> {
            var publications: List<Publication>? = null
            var packPublicationPointer: Int = -1
            var pageNumber: Int = 1

            override fun hasNext(): Boolean {
                if (publications == null || publications!!.size == packPublicationPointer + 1) {
                    publications = getPagePublications(hub, pageNumber++)
                    packPublicationPointer = 0
                }else {
                    packPublicationPointer += 1
                }

                return publications!!.size > packPublicationPointer && !rule.isTailPublication(publications!![packPublicationPointer])
            }

            override fun next(): Publication {
                return publications!![packPublicationPointer]
            }
        }
    }

    override fun isSupported(hub: Hub): Boolean {
        return true
    }

    fun getPagePublications(hub: Hub, pageNumber: Int): List<Publication> {
        val url: String = when (hub.type) {
            HubType.COMPANY -> "https://habr.com/ru/company/${hub.name}/blog/page$pageNumber"
            HubType.AUTHOR -> "https://habr.com/ru/users/${hub.name}/posts/page$pageNumber"
        }

        val html = call(url)
            ?: return emptyList()

        val articlesId: List<String> = Jsoup.parse(html).select("article").map { element -> element.attr("id") }

        return articlesId.map { id ->
            val article: Article = publicationInfo(id)

            Publication(SOURCE_NAME, "https://habr.com/ru/post/${article.id}/", hub, LocalDateTime.parse(article.publishedTime, DateTimeFormatter.ISO_DATE_TIME) )
        }
    }

    private fun call(url: String): String? {
        return client.newCall(Request.Builder().url(url).build()).execute().use { response ->
            if (response.code() != 200) null
            else response.body()?.string()
        }
    }

    private fun publicationInfo(articleId: String): Article {
        client.newCall(Request.Builder().url("https://habr.com/kek/v2/articles/$articleId").build()).execute().use {
            return it.body()?.string().let { mapper.readValue(it, Article::class.java) }
        }
    }
}