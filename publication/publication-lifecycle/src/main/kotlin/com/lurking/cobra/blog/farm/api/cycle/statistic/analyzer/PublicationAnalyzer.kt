package com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer

import com.lurking.cobra.blog.farm.api.publication.Publication

interface PublicationAnalyzer<T> {

    fun enrich(publication: Publication, scanning : T)
}