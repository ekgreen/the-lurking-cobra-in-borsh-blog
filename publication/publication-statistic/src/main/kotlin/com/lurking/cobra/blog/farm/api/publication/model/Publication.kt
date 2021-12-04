package com.lurking.cobra.blog.farm.api.publication.model

data class Publication(
    // уникальный идентификатор публикации в мастер-системе
    var id: String? = null,
    // идентификатор источника ресурса (публикации)
    val urn: String,
    // идентификатор ресурса (публикации)
    val uri: String,
    // рейтинг ресурса (публикации)
    var rating: Double = 0.0,
    // тэги проставленные автором ресурса (публикации)
    var tags: Set<String>? = null,
    // стратегия публикации ресурса (публикации)
    var strategy: PublicationStrategy? = null,
    // статистика из источника ресурса (публикации)
    var statistic: PublicationStatistic? = null,
    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = null
)