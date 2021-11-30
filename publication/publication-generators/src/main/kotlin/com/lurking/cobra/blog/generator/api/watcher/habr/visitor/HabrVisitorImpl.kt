package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

class HabrVisitorImpl(private val client: OkHttpClient) : HabrVisitor {

    override fun visitHub(hub: Hub, rule: TailVisitorRule): Iterator<Publication> {
        return object : Iterator<Publication> {
            var publications: List<Publication>? = null
            var pointer: Int = -1
            var pageNumber: Int = 1

            override fun hasNext(): Boolean {
                if (publications == null || publications!!.size == pointer)
                    publications = getPagePublications(hub, pageNumber++)

                pointer += 1

                return publications!!.size > pointer && !rule.isTailPublication(publications!![pointer])
            }

            override fun next(): Publication {
                return publications!![pointer]
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

        val html = call(url) ?: return emptyList()

        return Jsoup.parse(html).select("article").asSequence()
            .map { element -> element.attr("id") }
            .map { hubId -> Publication(id = hubId, urn = "habr") }
            .toList()
    }

    private fun call(url: String): String? {
        return client.newCall(Request.Builder().url(url).build()).execute().use { response ->
            if (response.code() != 200) null
            else response.body()?.string()
        }
    }
}