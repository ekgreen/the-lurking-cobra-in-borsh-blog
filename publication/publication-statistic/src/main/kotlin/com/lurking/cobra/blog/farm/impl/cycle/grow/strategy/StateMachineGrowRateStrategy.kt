package com.lurking.cobra.blog.farm.impl.cycle.grow.strategy

import com.lurking.cobra.blog.farm.api.cycle.grow.repository.RatingBasedSchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.GrowStrategy
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.RateState
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.StateSelector
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import java.util.concurrent.TimeUnit

/**
 *
 *
 * s1 <-> s2 <-> ... <-> sN
 */
class StateMachineGrowRateStrategy(
    private val states: Array<RateState>,
    private val selector: StateSelector = ApproximateStateSelector()
) : GrowStrategy<RatingBasedSchedulingTask, Double> {

    override fun estimateTaskDelay(previousTask: RatingBasedSchedulingTask?, newValue: Double, strategy: PublicationStrategy): Long {
        if (previousTask == null)
            return initStateMachine(newValue, strategy).duration.toMillis()

        // 1. выберем предыдущее состояние (или наиболее подходящее для обратной совместимости можно использовать гибкие выборки состояний)
        val selectedStateId = selector.selectStateByMillis(previousTask.growTime, states)

        // 2. получим состояния из машины состояний (у нас просто массив)
        var state: RateState = states[selectedStateId]

        if(previousTask.growRate != 0.0 || newValue != 0.0) {
            val shiftValue = ((previousTask.growRate - newValue) / (previousTask.growRate + newValue) / 2 ) * 100

            state = when {
                (leftStateAvailable(selectedStateId, shiftValue)) -> states[selectedStateId - 1]
                (rightStateAvailable(selectedStateId, shiftValue)) -> states[selectedStateId + 1]
                else -> state
            }
        }

        return state.duration.toMillis()
    }

    private fun leftStateAvailable(selectedStateId: Int, shiftValue: Double): Boolean {
        return selectedStateId - 1 >= 0 && states[selectedStateId - 1].lowBound <= shiftValue
    }

    private fun rightStateAvailable(selectedStateId: Int, shiftValue: Double): Boolean {
        return selectedStateId + 1 < states.size && states[selectedStateId].lowBound > shiftValue
    }

    private fun initStateMachine(newValue: Double, strategy: PublicationStrategy): RateState {
        return states[when (strategy) {
            PublicationStrategy.PUBLISHING  -> selector.selectStateByMillis(TimeUnit.HOURS.toMillis(6), states)
            PublicationStrategy.GROW        -> selector.selectStateByMillis(TimeUnit.DAYS.toMillis(1), states)
            PublicationStrategy.FREEZE      -> selector.selectStateByMillis(TimeUnit.DAYS.toMillis(30), states)
        }]
    }

}