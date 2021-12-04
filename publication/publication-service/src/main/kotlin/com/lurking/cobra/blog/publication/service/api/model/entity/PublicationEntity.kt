package com.lurking.cobra.blog.publication.service.api.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(collection = "publication")
data class PublicationEntity(
    @Id var id: String? = null,

    @Field("uri")
    var uri: String,

    @Field("title")
    var title: String,

    @Field("rating")
    var rating: Double,

    @Field("tags")
    var tags: MutableSet<String> = mutableSetOf(),

    @Field("key_words")
    var key_words: MutableSet<String> = mutableSetOf(),

    @Field("reactions")
    var reactions: MutableMap<String, Int> = mutableMapOf("like" to 0, "comments" to 0, "zipper" to 0),

    @Field("status")
    var status: Int,

    @Field("last_publication")
    var last_publication: Date? = null,

    @Field("publication_count")
    var publication_count: Int = 0
) {
}

