package com.lurking.cobra.blog.farm.api.cycle.statistic

import com.lurking.cobra.blog.farm.api.publication.PublicationStrategy
import com.lurking.cobra.blog.farm.api.publication.Publication

interface PublicationStatisticCycle {

    fun doPublicationCycle(publication: Publication, publisher: (Publication,PublicationStrategy) -> Unit)
}