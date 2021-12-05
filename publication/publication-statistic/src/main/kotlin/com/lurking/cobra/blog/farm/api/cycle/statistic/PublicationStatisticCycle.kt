package com.lurking.cobra.blog.farm.api.cycle.statistic

import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface PublicationStatisticCycle {

    fun doPublicationCycle(publication: Publication, publisher: (Publication, PublicationStrategy) -> Unit)
}