package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Vote
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Voter
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import mu.KLogging

class AnyTagVoter(private val requiredTagsCollection: List<Set<String>>) : Voter {

    override fun vote(publication: Publication): Vote {
        val tags: Set<String> = publication.tags?: return Vote.ABSTAINED

        if (requiredTagsCollection.any { tags.containsAll(it) }) {
            logger.info { "Any tag voter [log, urn = ${publication.urn}, uri = ${publication.uri} ] publication consist of required tags; vote is publishing" }

            return Vote.PUBLISH
        } else {
            logger.warn {
                "Any tag voter [log, urn = ${publication.urn}, uri = ${publication.uri} ] publication not contains any requred tag's set; vote is decline." +
                        "\n- Publication = $publication\n- requred tags sets: ${requiredTagsCollection}"
            }

            return Vote.DECLINE
        }
    }

    companion object : KLogging()
}