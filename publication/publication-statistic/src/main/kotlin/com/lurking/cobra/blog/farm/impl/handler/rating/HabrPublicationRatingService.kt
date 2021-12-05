package com.lurking.cobra.blog.farm.impl.handler.rating

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStatistic
import com.lurking.cobra.blog.farm.api.publication.rating.UrnPublicationRatingService
import com.lurking.cobra.blog.farm.impl.cycle.statistic.habr.HabrStatisticManager.Companion.SOURCE_NAME

class HabrPublicationRatingService : UrnPublicationRatingService {

    //@formatter:off
    companion object {
        const val K_COMMENT  : Double = 0.3
        const val K_FAVORITES: Double = 0.3
        const val K_VOTES    : Double = 0.4

        const val B_COMMENT  : Double = 10.0
        const val B_FAVORITES: Double = 100.0
        const val B_VOTES    : Double = 30.0
    }


    override fun publicationStatisticRating(publication: Publication): Double {
        val statistic: PublicationStatistic = publication.statistic ?: return 0.0

        return (
                ( ( (statistic.commentsCount ?: 0) / B_COMMENT   ) * K_COMMENT   )
             +  ( ( (statistic.favoritesCount?: 0) / B_FAVORITES ) * K_FAVORITES )
             +  ( ( (statistic.votesCount    ?: 0) / B_VOTES     ) * K_VOTES     )
        )
    }
    //@formatter:on

    override fun publicationReactionRating(publication: Publication): Double? = null

    override fun supportedUrn(): String = SOURCE_NAME
}