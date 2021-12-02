package com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold

interface ThresholdValueProducer {

    /**
     *
     */
    fun publishingLowBound(): Double

    /**
     *
     *
     */
    fun freezeUpperBound(): Double
}