package com.lurking.cobra.blog.farm.api.cycle.statistic.habr

interface HabrStatisticVisitor {

    /**
     *
     *
     */
    fun publicationStatistic(articleId: String): HabrArticleStatistic

    /**
     *
     *
     */
    fun publicationText(articleId: String): String

}