package com.lurking.cobra.blog.farm.configuration

import com.lurking.cobra.blog.farm.PublicationStatisticApplication.Companion.DEV_PROFILE
import com.lurking.cobra.blog.farm.PublicationStatisticApplication.Companion.PROD_PROFILE
import com.lurking.cobra.blog.farm.api.cycle.grow.GrowService
import com.lurking.cobra.blog.farm.api.cycle.grow.config.GrowServiceConfiguration
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.RatingBasedSchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.RatingBasedSchedulingTaskRepository
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTask
import com.lurking.cobra.blog.farm.api.cycle.grow.repository.SchedulingTaskRepository
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.GrowStrategy
import com.lurking.cobra.blog.farm.api.cycle.grow.strategy.RateState
import com.lurking.cobra.blog.farm.api.cycle.selection.SelectionStrategy
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.SelectionFilter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.SelectionServiceConfiguration
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.threshold.ThresholdValueProducer
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.Voter
import com.lurking.cobra.blog.farm.api.cycle.selection.filter.voter.VoterStrategy
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.publication.PublicationApi
import com.lurking.cobra.blog.farm.api.publication.publisher.PublicationPublisher
import com.lurking.cobra.blog.farm.api.publication.rating.RatingCountingStrategy
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleScheduler
import com.lurking.cobra.blog.farm.api.reciever.PublicationLifecycleSchedulerConfiguration
import com.lurking.cobra.blog.farm.impl.cycle.grow.RatingScheduledGrowService
import com.lurking.cobra.blog.farm.impl.cycle.grow.strategy.PropertyGrowStrategyConfiguration
import com.lurking.cobra.blog.farm.impl.cycle.grow.strategy.StateMachineGrowRateStrategy
import com.lurking.cobra.blog.farm.impl.cycle.selection.FilteringSelectionStrategy
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.PrepareSelectionFilter
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.SelectionExceptionFilter
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold.PropertiesThresholdValueProducer
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.threshold.ThresholdFilter
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter.AnyTagVoter
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter.AtLeastOneVoterStrategy
import com.lurking.cobra.blog.farm.impl.cycle.selection.filter.voter.PropertyVoterConfiguration
import com.lurking.cobra.blog.farm.impl.handler.publisher.LoggerPublicationPublisher
import com.lurking.cobra.blog.farm.impl.handler.publisher.ApiPublicationPublisher
import com.lurking.cobra.blog.farm.impl.handler.rating.ArithmeticMeanStrategy
import com.lurking.cobra.blog.farm.impl.reciever.EvenlyPublicationLifecycleScheduler
import com.lurking.cobra.blog.farm.impl.reciever.PropertyPublicationLifecycleSchedulerConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

@Configuration
class StatisticServiceConfiguration {

    // publication

    @Bean
    @Profile(DEV_PROFILE)
    fun loggerPublicationPublisher(): PublicationPublisher {
        return LoggerPublicationPublisher()
    }

    @Bean
    @Profile(PROD_PROFILE)
    fun servicePublicationPublisher(api: PublicationApi): PublicationPublisher {
        return ApiPublicationPublisher(api)
    }

    // statistic

    @Bean
    fun arithmeticMeanStrategy(): RatingCountingStrategy {
        return ArithmeticMeanStrategy()
    }

    // selection

    @Bean
    fun filteringSelectionStrategy(filters: List<SelectionFilter>): SelectionStrategy {
        return FilteringSelectionStrategy(filters.sortedBy { it.order })
    }

    @Bean
    fun prepareSelectionFilter(strategy: VoterStrategy): SelectionFilter {
        return PrepareSelectionFilter(strategy)
    }

    @Bean
    fun atLeastOneVoterStrategy(voters: List<Voter>): VoterStrategy {
        return AtLeastOneVoterStrategy(voters)
    }

    @Bean
    fun anyTagVoter(configuration: PropertyVoterConfiguration): Voter {
        return AnyTagVoter(configuration.tags)
    }

    @Bean
    fun selectionExceptionFilter(): SelectionFilter {
        return SelectionExceptionFilter()
    }

    @Bean
    fun thresholdFilter(holdValueResolver: ThresholdValueProducer): SelectionFilter {
        return ThresholdFilter(holdValueResolver)
    }

    @Bean
    fun propertiesThresholdValueProducer(properties: SelectionServiceConfiguration): ThresholdValueProducer{
        return PropertiesThresholdValueProducer(properties)
    }

    // grow

    @Bean
    fun ratingScheduledGrowService(growStrategy: GrowStrategy<RatingBasedSchedulingTask, Double>, repository: RatingBasedSchedulingTaskRepository): GrowService {
        return RatingScheduledGrowService(growStrategy, repository)
    }

    @Bean
//    @ConditionalOnProperty("publication.grow.strategy.machine")
    fun stateMachineGrowRateStrategy(
        configuration: PropertyGrowStrategyConfiguration
    ) : GrowStrategy<RatingBasedSchedulingTask, Double> {
        return StateMachineGrowRateStrategy(arrayOf( // todo configuration based
            RateState(50, Duration.of(1, ChronoUnit.MINUTES)),
            RateState(20, Duration.of(5, ChronoUnit.MINUTES)),
            RateState(10, Duration.of(10, ChronoUnit.MINUTES)),
            RateState(0, Duration.of(60, ChronoUnit.MINUTES)),
        ))
    }

    @Bean
    fun evenlyPublicationLifecycleScheduler(
        repositories: SchedulingTaskRepository<out SchedulingTask>,
        scheduler: ScheduledExecutorService,
        configuration: PublicationLifecycleSchedulerConfiguration,
        publicationHandler: PublicationHandler
    ): PublicationLifecycleScheduler {
        return EvenlyPublicationLifecycleScheduler(repositories, scheduler, configuration, publicationHandler)
    }

    @Bean
    fun scheduledExecutorService(properties: GrowServiceConfiguration): ScheduledExecutorService {
        return Executors.newScheduledThreadPool(properties.scheduler?.threads?: 4)
    }

    @Bean
    fun propertyPublicationLifecycleSchedulerConfiguration(properties: GrowServiceConfiguration): PublicationLifecycleSchedulerConfiguration {
        return PropertyPublicationLifecycleSchedulerConfiguration(properties)
    }
}