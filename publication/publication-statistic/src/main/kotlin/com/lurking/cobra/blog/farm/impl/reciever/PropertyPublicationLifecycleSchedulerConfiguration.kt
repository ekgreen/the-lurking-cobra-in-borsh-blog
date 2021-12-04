package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.api.cycle.grow.config.GrowServiceConfiguration
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleSchedulerConfiguration

class PropertyPublicationLifecycleSchedulerConfiguration(private val properties: GrowServiceConfiguration) : PublicationLifecycleSchedulerConfiguration {

    companion object {
        const val DEFAULT_DELAY: Long = 3600
    }

    override fun getSchedulingDelay(): Long {
        return properties.scheduler?.delay?: DEFAULT_DELAY
    }

}