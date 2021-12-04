package com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold

import com.lurking.cobra.blog.farm.api.publication.model.Publication

interface ThresholdValueProducer {

    /**
     *
     *
     */
    fun publishingLowBound(publication: Publication): Double

    /**
     *
     *
     */
    fun freezeUpperBound(publication: Publication): Double
}