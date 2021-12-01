package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Vote
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Voter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.VoterStrategy
import com.lurking.cobra.blog.farm.api.publication.Publication
import com.lurking.cobra.blog.farm.impl.exception.PublicationDeniedException

class AtLeastOneVoterStrategy(private val voters: List<Voter>) : VoterStrategy {
    override fun makeDecision(publication: Publication) {
        if(voters.none { voter -> voter.vote(publication) == Vote.PUBLISH })
            throw PublicationDeniedException("publication not allowed to publishing process")
    }
}