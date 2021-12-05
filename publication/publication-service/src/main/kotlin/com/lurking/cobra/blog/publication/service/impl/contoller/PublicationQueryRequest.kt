package com.lurking.cobra.blog.publication.service.impl.contoller

data class PublicationQueryRequest(
    var tags: Set<String>?,
    var count: Int = 10
)
