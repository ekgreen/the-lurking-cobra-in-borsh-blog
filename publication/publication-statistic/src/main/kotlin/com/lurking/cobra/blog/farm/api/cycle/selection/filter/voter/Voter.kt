package com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter

import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface Voter {

    fun vote(publication: Publication): Vote
}