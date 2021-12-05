package com.lurking.cobra.blog.farm.api.publication.publisher

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

interface PublicationPublisher {

    /**
     * Опубликовать статью во внешний или внутренний сервис, например
     * это может быть запрос в сервис "Публикаций" для отправки обновленных
     * данных по публикации
     *
     * @param publication публикация
     */
    fun publish(publication: Publication, strategy: PublicationStrategy)
}