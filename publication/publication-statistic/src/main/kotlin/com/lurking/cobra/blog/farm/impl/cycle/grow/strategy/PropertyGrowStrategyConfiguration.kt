package com.lurking.cobra.blog.farm.impl.cycle.grow.strategy

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

@Configuration
@ConfigurationProperties(prefix = "publication.grow.strategy.machine")
data class PropertyGrowStrategyConfiguration @ConstructorBinding constructor(
    val states: List<MachineState>
)

data class MachineState @ConstructorBinding constructor(
    val bound: Int,
    val duration: String
){
    fun getDuration(): Duration {
        val format: Array<String> = SPLIT_PATTERN.split(duration)

        return Duration.of(format[0].toLong(), if(format.size == 1) ChronoUnit.MILLIS else when(format[1]){
            "h" -> ChronoUnit.HOURS
            "m" -> ChronoUnit.MINUTES
            "s" -> ChronoUnit.SECONDS
            "d" -> ChronoUnit.DAYS
            "M" -> ChronoUnit.MONTHS
            "y" -> ChronoUnit.YEARS
            else -> ChronoUnit.MILLIS
        })
    }
}

val SPLIT_PATTERN: Pattern = Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")
