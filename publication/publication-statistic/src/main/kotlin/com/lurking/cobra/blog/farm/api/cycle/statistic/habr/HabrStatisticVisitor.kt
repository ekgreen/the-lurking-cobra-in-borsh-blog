package com.lurking.cobra.blog.farm.api.cycle.statistic.habr

import com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article.Article

interface HabrStatisticVisitor {

    /**
     *
     *
     */
    fun publicationStatistic(articleId: String): Article

}