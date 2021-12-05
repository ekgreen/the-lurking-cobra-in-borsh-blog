package com.lurking.cobra.blog.publication.service.api.model

data class PublicationReactions(
    var likesCount      : Int = 0,
    var lightingCount   : Int = 0,
    var pokerFaceCount  : Int = 0,
    var commentCount    : Int = 0,
) {

    fun handleReaction(reaction: String, count: Int){
        when(reaction){
            "like"          -> likesCount       += count
            "zipper"        -> lightingCount    += count
            "poker_face"    -> pokerFaceCount   += count
            "comment"       -> commentCount     += count
        }
    }
}
