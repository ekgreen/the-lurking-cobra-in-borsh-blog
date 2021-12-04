package com.lurking.cobra.blog.farm.api.publication.model

data class PublicationRating (
    // тип подсчитываемого рейтинга
    val type: RatingType,
    // рейтинг (может быть null, если подсчет не производился)
    val rating: Double?
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicationRating

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}
