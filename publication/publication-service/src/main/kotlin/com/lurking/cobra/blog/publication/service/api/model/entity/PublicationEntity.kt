package com.lurking.cobra.blog.publication.service.api.model.entity

import com.lurking.cobra.blog.publication.service.api.model.PublicationReactions
import com.lurking.cobra.blog.publication.service.api.model.PublicationStatistic
import com.lurking.cobra.blog.publication.service.api.model.PublicationStrategy
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(collection = "publication")
data class PublicationEntity(
    @Id var id: String? = null,

    @Field("uri")
    var uri: String,

    @Field("urn")
    var urn: String,

    @Field("title")
    var title: String,

    @Field("rating")
    var rating: Double,

    @Field("tags")
    var tags: MutableSet<String>,

    @Field("key_words")
    var keywords: MutableSet<String>,

    @Field("strategy")
    var strategy: PublicationStrategy,

    @Field("statistic")
    var statistic: PublicationStatistic,

    @Field("reaction_rating")
    var reactionRating: Double?,

    @Field("reactions")
    var reactions: PublicationReactions,

    @Field("last_publication")
    var lastPublicationDate: Date?,

    @Field("publication_count")
    var publicationsCount: Long = 0
) {
}

