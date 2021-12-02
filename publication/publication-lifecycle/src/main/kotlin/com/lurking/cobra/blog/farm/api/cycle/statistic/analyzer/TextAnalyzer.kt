package com.lurking.cobra.blog.farm.api.cycle.statistic.analyzer

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TextAnalyzer
