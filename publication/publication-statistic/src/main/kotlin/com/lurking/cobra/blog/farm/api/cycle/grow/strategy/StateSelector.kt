package com.lurking.cobra.blog.farm.api.cycle.grow.strategy

interface StateSelector {

    fun selectStateByMillis(time: Long, states: Array<RateState>): Int
}