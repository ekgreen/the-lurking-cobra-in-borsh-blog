package com.lurking.cobra.blog.bot.api.publication

data class PublicationQueryRequest(
    var tags: Set<String>?,
    var count: Int = 10
)
