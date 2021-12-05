package com.lurking.cobra.blog.bot.api.publication


data class Publication(
    // идентификатор публикации
    var id: String,

    // заголовок ресурса (публикации)
    var title: String,

    // уникальный идентификатор публикации (ресурса)
    var uri: String,

    // рейтинг ресурса (публикации)
    var rating: Double = 0.0,

    // теги ресурса (публикации)
    var tags: Set<String> = mutableSetOf(),

    // ключевые слова ресурса (публикации)
    var keywords: Set<String> = mutableSetOf(),

    // реакция на ресурс после публикации
    var reactions: PublicationReactions? = PublicationReactions(),
)