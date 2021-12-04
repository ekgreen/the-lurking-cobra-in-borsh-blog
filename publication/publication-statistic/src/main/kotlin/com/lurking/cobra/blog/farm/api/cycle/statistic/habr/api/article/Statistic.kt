package com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article


data class Statistic (
	val commentsCount 	: Int? = 0,
	val favoritesCount 	: Int? = 0,
	val readingCount 	: Int? = 0,
	val score 			: Int? = 0,
	val votesCount 		: Int? = 0
)