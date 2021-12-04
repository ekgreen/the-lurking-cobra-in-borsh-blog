package com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold

import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.SelectionServiceConfiguration
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.ThresholdValueProducer
import com.lurking.cobra.blog.farm.api.publication.model.Publication

class PropertiesThresholdValueProducer(private val properties: SelectionServiceConfiguration): ThresholdValueProducer {

    companion object {
        const val DEFAULT_PUBLISH_BOUND: Double = 0.7
        const val DEFAULT_FREEZE_BOUND : Double = 0.3
    }

    override fun publishingLowBound(publication: Publication): Double {
        return properties.filters?.threshold?.publishing?: 0.7
    }

    override fun freezeUpperBound(publication: Publication): Double {
        return properties.filters?.threshold?.freeze?: 0.3
    }
}