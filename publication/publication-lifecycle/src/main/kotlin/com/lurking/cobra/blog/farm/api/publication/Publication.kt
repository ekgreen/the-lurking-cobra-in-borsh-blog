package com.lurking.cobra.blog.farm.api.publication

data class Publication(
    // уникальный идентификатор публикации в мастер-системе
    val id: Int? = null,
    // идентификатор источника ресурса (публикации)
    val urn: String,
    // идентификатор ресурса (публикации)
    val uri: String,
    // статистика из источника ресурса публикации
    var statistic: PublicationStatistic? = null
)