package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        val id = Jsoup.parse(html).select("article")
            .map { element -> element.attr("id") }
            .toList()

        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateString = Jsoup.parse(html).select("time")
            .map { element -> element.attr("datetime") }
            .toMutableList()
        val date: List<LocalDateTime> = dateString
            .map { dateStr -> LocalDateTime.parse(dateStr, dateTimeFormatter) }

        //Тут если парсить по датам, как я сделал, то находится в самом низу (2 если компания, 1 если пользователь
        //какие-то древние даты, которые даже не отображаются на странице, обрезаю их
        if (url.contains("/company/")) {
            dateString.removeAt(dateString.lastIndex)
        }
        dateString.removeAt(dateString.lastIndex)

        val pagePublications: List<Publication> = id.map { Publication(it, "habr") }

        //мб я тотал додик, но не пришло в голову задать даты постов лучше, чем так, хотя думаю он есть
        for (i in 0 until 20) {
            pagePublications[i].timestamp = date[i]
        }

        return pagePublications
    }

    private fun call(url: String): String? {
        return client.newCall(Request.Builder().url(url).build()).execute().use { response ->
            if (response.code() != 200) null
            else response.body()?.string()
        }
    }
}