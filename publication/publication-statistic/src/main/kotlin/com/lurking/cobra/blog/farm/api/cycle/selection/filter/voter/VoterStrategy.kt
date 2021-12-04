package com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter

import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface VoterStrategy {

    fun makeDecision(publication: Publication)
}