package com.lurking.cobra.blog.farm.api.publication.model

import com.fasterxml.jackson.annotation.JsonCreator

data class PublicationDto @JsonCreator constructor(
    // идентификатор публикации
    var id: String? = null,

    // уникальный идентификатор публикации (ресурса)
    var uri: String? = null,

    // источник публикации (запроса на публикацию)
    var urn: String? = null,

    // стратегия публикации ресурса
    var strategy: PublicationStrategy? = null,

    // рейтинг ресурса (публикации)
    var rating: Double = 0.0,

    // статистика из источника ресурса публикации
    var statistic: PublicationStatistic? = null,

    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = null
)