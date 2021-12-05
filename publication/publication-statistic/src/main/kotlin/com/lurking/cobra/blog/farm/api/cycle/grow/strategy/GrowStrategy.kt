package com.lurking.cobra.blog.farm.api.cycle.grow.strategy

import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

interface GrowStrategy<T,R> {

    /**
     *
     *
     */
    fun estimateTaskDelay(previousTask: T?, newValue: R, strategy: PublicationStrategy): Long

}