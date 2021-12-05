package com.lurking.cobra.blog.generator.api.watcher.habr.recommendation

interface HabrRecommendation {

    fun handleRecommendation(recommendation: Recommendation)
}