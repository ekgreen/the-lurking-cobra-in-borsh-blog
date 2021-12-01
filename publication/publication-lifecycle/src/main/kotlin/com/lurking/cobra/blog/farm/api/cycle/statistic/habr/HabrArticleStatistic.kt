package com.lurking.cobra.blog.farm.api.cycle.statistic.habr

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class HabrArticleStatistic @JsonCreator constructor(
    @JsonProperty("commentsCount")  val commentsCount    : Int,
    @JsonProperty("favoritesCount") val favoritesCount   : Int,
    @JsonProperty("readingCount")   val readingCount     : Int,
    @JsonProperty("score")          val score            : Int,
    @JsonProperty("votesCount")     val votesCount       : Int,
)
