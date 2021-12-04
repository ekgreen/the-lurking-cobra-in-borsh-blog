package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Vote
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Voter
import com.lurking.cobra.blog.farm.api.publication.model.Publication

class AnyTagVoter(private val requiredTagsCollection: List<Set<String>>): Voter {

    override fun vote(publication: Publication): Vote {
        val tags: Set<String> = publication.tags ?: return Vote.ABSTAINED

        return if(requiredTagsCollection.any { tags.containsAll(it) }) Vote.PUBLISH else Vote.DECLINE
    }
}