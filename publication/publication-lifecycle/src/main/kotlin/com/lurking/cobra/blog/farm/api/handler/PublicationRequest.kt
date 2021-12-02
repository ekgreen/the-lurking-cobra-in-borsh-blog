package com.lurking.cobra.blog.farm.api.handler

data class PublicationRequest (
    // идентификатор публикации
    var id: String? = null,

    // уникальный идентификатор публикации (ресурса)
    var uri: String? = null,

    // источник публикации (запроса на публикацию)
    var urn: String? = null,
)