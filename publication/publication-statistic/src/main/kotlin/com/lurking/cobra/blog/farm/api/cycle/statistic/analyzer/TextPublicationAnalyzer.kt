package com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer

import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface TextPublicationAnalyzer : PublicationAnalyzer<String> {

    override fun enrich(publication: Publication, scanning : String)
}