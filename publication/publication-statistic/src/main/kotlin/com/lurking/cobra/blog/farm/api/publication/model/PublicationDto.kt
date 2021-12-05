package com.lurking.cobra.blog.farm.api.publication.model

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

data class PublicationDto constructor(
    // идентификатор публикации
    var id: String? = null,

    // заголовок ресурса (публикации)
    var title: String? = null,

    // уникальный идентификатор публикации (ресурса)
    var uri: String? = null,

    // источник публикации (запроса на публикацию)
    var urn: String? = null,

    // рейтинг ресурса (публикации)
    var rating: Double = 0.0,

    // теги ресурса (публикации)
    var tags: Set<String>? = null,

    // ключевые слова ресурса (публикации)
    var keywords: Set<String>? = null,

    // стратегия публикации ресурса
    var strategy: PublicationStrategy? = null,

    // статистика из источника ресурса публикации
    var statistic: PublicationStatistic? = null,

    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = null,

    // рейтинг реакций на ресурс (публикации)
    var reactionRating: Double? = null,

    // кол-во публикаций
    var publicationsCount: Long? = null,

    // последняя дата публикации
    var lastPublicationDate: Date? = null
)