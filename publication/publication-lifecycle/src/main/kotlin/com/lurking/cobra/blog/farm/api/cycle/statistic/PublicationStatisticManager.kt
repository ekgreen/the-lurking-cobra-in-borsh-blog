package com.lurking.cobra.blog.farm.api.cycle.statistic

import com.lurking.cobra.blog.farm.api.publication.Publication

interface PublicationStatisticManager {

    /**
     * Обогатить публикацию новыми статистическими данными
     *
     * @param publication - публикация
     */
    fun enrich(publication: Publication)

    /**
     * Поддерживаемы тип ресурсов
     *
     * @param publicationSourceName - название источника публикации
     * @return true - поддерживается, false - не поддерживается
     */
    fun isSupported(publicationSourceName: String): Boolean
}