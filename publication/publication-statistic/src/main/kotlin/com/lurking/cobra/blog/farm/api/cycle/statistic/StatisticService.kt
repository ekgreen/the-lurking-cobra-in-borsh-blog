package com.lurking.cobra.blog.farm.api.cycle.statistic

import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface StatisticService {

    /**
     * Обогатить публикацию новыми статистическими данными
     *
     * @param publication - публикация
     */
    fun enrich(publication: Publication)
}