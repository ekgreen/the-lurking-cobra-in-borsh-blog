package com.lurking.cobra.blog.generator.api.publication

import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import java.time.LocalDateTime

data class Publication(
    // идентификатор источника ресурса (публикации)
    val urn: String,
    // идентификатор ресурса (публикации)
    val uri: String,
    // хаб, из которого была получена публикация
    val hub: Hub,
    // дата публикации ресурса (публикации)
    var timestamp: LocalDateTime
)