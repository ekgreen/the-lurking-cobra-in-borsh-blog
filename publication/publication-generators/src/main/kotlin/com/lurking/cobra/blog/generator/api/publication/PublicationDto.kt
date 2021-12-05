package com.lurking.cobra.blog.generator.api.publication

data class PublicationDto(
    // идентификатор источника ресурса (публикации)
    val urn: String,
    // идентификатор ресурса (публикации)
    val uri: String,
)