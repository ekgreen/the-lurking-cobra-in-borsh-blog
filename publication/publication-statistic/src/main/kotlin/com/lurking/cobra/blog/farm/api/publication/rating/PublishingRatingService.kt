package com.lurking.cobra.blog.farm.api.publication.rating

import com.lurking.cobra.blog.farm.api.publication.model.Publication


interface PublishingRatingService {

    /**
     * Расчет рейтинга публикации
     *
     * рейтинг публикации - число с плавающей точкой, обозначающее возможность добавления
     * публикации в сервис "Публикаций" или иной в статусе PUBLISH
     *
     * @param publication - публикация
     * @return рейтинг публикации
     *
     * @see PublicationStrategy
     */
    fun publishingRate(publication: Publication): Double
}