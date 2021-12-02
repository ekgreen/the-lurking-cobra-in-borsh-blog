package com.lurking.cobra.blog.farm.impl.cycle.grow.strategy

import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.RateState
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.StateSelector
import kotlin.math.abs

class ApproximateStateSelector : StateSelector {

    override fun selectStateByMillis(time: Long, states: Array<RateState>): Int {
        return states.withIndex().minByOrNull { abs(time - it.value.duration().toMillis()) }!!.index
    }
}