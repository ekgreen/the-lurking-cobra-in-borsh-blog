package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.fasterxml.jackson.annotation.JsonProperty

data class Article (
    @JsonProperty("id") 			val id 				: Int,
    @JsonProperty("timePublished") 	val publishedTime 	: String,
)
