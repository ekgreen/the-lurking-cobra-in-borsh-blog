package com.lurking.cobra.blog.publication.service.api.model

import java.util.*


data class Publication(
    // идентификатор публикации
    var id: String? = null,

    // заголовок ресурса (публикации)
    var title: String,

    // уникальный идентификатор публикации (ресурса)
    var uri: String? = null,

    // источник публикации (запроса на публикацию)
    var urn: String? = null,

    // рейтинг ресурса (публикации)
    var rating: Double = 0.0,

    // теги ресурса (публикации)
    var tags: Set<String>? = mutableSetOf(),

    // ключевые слова ресурса (публикации)
    var keywords: Set<String>? = mutableSetOf(),

    // стратегия публикации ресурса
    var strategy: PublicationStrategy? = PublicationStrategy.FREEZE,

    // статистика из источника ресурса публикации
    var statistic: PublicationStatistic? = PublicationStatistic(),

    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = PublicationReactions(),

    // рейтинг реакций на ресурс (публикации)
    var reactionRating: Double?,

    // кол-во публикаций
    var publicationsCount: Long? = 0,

    // последняя дата публикации
    var lastPublicationDate: Date? = null
)