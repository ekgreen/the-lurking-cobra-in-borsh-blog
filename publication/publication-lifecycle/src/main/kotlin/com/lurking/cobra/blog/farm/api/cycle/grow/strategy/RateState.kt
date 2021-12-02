package com.lurking.cobra.blog.farm.api.cycle.grow.strategy

import java.time.Duration

interface RateState {

    /**
     *
     *
     */
    fun lowBound(): Int

    /**
     *
     *
     */
    fun duration(): Duration
}