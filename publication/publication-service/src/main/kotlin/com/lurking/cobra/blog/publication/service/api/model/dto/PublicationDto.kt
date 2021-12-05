package com.lurking.cobra.blog.publication.service.api.model.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.lurking.cobra.blog.publication.service.api.model.PublicationReactions
import com.lurking.cobra.blog.publication.service.api.model.PublicationStatistic
import com.lurking.cobra.blog.publication.service.api.model.PublicationStrategy
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PublicationDto @JsonCreator constructor(
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
    var tags: Set<String>? = mutableSetOf(),

    // ключевые слова ресурса (публикации)
    var keywords: Set<String>? = mutableSetOf(),

    // стратегия публикации ресурса
    var strategy: PublicationStrategy? = null,

    // статистика из источника ресурса публикации
    var statistic: PublicationStatistic? = PublicationStatistic(),

    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = PublicationReactions(),

    // кол-во публикаций
    var publicationsCount: Long? = 0,

    // последняя дата публикации
    var lastPublicationDate: Date? = null
)