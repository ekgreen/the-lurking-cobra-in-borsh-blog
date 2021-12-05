package com.lurking.cobra.blog.bot.api.flow.recommendation

import java.util.regex.Pattern

enum class PublicationSource(val value: String, val patter: Pattern) {
    HABR("Хабр", Pattern.compile("^https://habr.com/ru/post/[0-9]+/$")),
    UNKNOWN("Другой", Pattern.compile("^https://.+$"));
}

fun findSourceByText(text: String): PublicationSource {
    return PublicationSource.values().find { it.value.equals(text) }!!
}