package com.lurking.cobra.blog.farm.impl.cycle.statistic.habr

import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticManager
import com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer.TextAnalyzer
import com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer.TextPublicationAnalyzer
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.HabrArticleStatistic
import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.HabrStatisticVisitor
import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.api.publication.PublicationStatistic
import org.springframework.lang.Nullable

class  HabrStatisticManager(
    private val visitor: HabrStatisticVisitor,
    @Nullable @TextAnalyzer
    private val analyzers: List<TextPublicationAnalyzer> = emptyList()
) : PublicationStatisticManager {

    override fun enrich(publication: Publication) {
        // 1. Загрузить статистику по статье
        val statistic: HabrArticleStatistic = visitor.publicationStatistic(publication.uri)

        // 2. Обогатим публикацию обновленной статистикой данными
        applyStatistic(publication, statistic)

        // 3. Если публикация "новая", то запустить дополнительные анализаторы обработчики
        if(publication.id == null)
            analyzePublication(publication)
    }

    private fun applyStatistic(publication: Publication, statistic: HabrArticleStatistic) {
        publication.statistic = PublicationStatistic(
            commentsCount   = statistic.commentsCount,
            favoritesCount  = statistic.favoritesCount,
            readingCount    = statistic.readingCount,
            score           = statistic.score,
            votesCount      = statistic.votesCount,
        )
    }

    private fun analyzePublication(publication: Publication) {
        // 1. Загрузить текст статьи
        val text: String = visitor.publicationText(publication.uri)

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