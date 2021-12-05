package com.lurking.cobra.blog.farm.api.cycle.grow.strategy

import java.time.Duration

data class RateState(
    val lowBound: Int,
    val duration: Duration
)