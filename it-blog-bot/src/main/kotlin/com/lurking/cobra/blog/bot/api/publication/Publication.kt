package com.lurking.cobra.blog.bot.api.publication

import com.lurking.cobra.blog.bot.api.flow.recommendation.Recommendation


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

fun createPublicationPost(post: Publication): String {
        return """
            <u><b>${post.title}</b></u>
            <pre>${post.tags.joinToString(separator = " ") { "#$it" }}</pre>

            <i>publication</i>: ${post.uri}
            """.trimIndent()
}

fun createRecommendationPost(recommendation: Recommendation): String {
    return """
        <u><b>${recommendation.title}</b></u>
        <b>Описание:</b> ${recommendation.description}

        <i>publication</i>: ${recommendation.link}
        """.trimIndent()
}