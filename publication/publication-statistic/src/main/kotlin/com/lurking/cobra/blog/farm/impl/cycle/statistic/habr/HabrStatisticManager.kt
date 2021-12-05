package com.lurking.cobra.blog.farm.impl.cycle.statistic.habr

import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticManager
import com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer.TextPublicationAnalyzer
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.HabrStatisticVisitor
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article.Article
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article.Statistic
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStatistic

class  HabrStatisticManager(
    private val visitor: HabrStatisticVisitor,
    private val analyzers: List<TextPublicationAnalyzer> = emptyList()
) : PublicationStatisticManager {

    override fun enrich(publication: Publication) {
        // 1. Загрузить статистику по статье
        val article: Article = visitor.publicationStatistic(publication.uri)

        // 2. Обогатим публикацию обновленной статистикой данными
        applyStatistic(publication, article)

        // 3. Обогатим публикацию обновленной статистикой по тегам
        applyTags(publication, article)

        // 4. Обогатим публикацию заголовком
        applyTitle(publication, article)

        // 5. Если публикация "новая", то запустить дополнительные анализаторы обработчики
        if(publication.id == null)
            analyzePublication(article, publication)
    }

    private fun applyStatistic(publication: Publication, article: Article) {
        val statistic: Statistic = article.statistic

        publication.statistic = PublicationStatistic(
            commentsCount   = statistic.commentsCount,
            favoritesCount  = statistic.favoritesCount,
            readingCount    = statistic.readingCount,
            score           = statistic.score,
            votesCount      = statistic.votesCount,
        )
    }

    private fun applyTags(publication: Publication, article: Article) {
        publication.tags = article.tags.map { it.value.lowercase() }.toSet()
    }


    private fun applyTitle(publication: Publication, article: Article) {
        publication.title = article.title
    }


    private fun analyzePublication(article: Article, publication: Publication) {
        // 1. Загрузить текст статьи
        val text: String = article.text

        // 2. Обогащаем публикацию метаданными
        analyzers.forEach{it.enrich(publication, text)}
    }

    override fun isSupported(publicationSourceName: String): Boolean {
        return SOURCE_NAME == publicationSourceName
    }

    companion object {
        const val SOURCE_NAME = "urn:habr"
    }
}