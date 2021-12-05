package com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article

import com.fasterxml.jackson.annotation.JsonProperty

data class Article (
	@JsonProperty("id") 			val id 				: Int,
	@JsonProperty("timePublished") 	val publishedTime 	: String,
	@JsonProperty("lang") 			val lang 			: String,
	@JsonProperty("titleHtml") 		val title 			: String,
	@JsonProperty("editorVersion") 	val version 		: Double,
	@JsonProperty("postType") 		val postType 		: String,
	@JsonProperty("statistics") 	val statistic 		: Statistic,
	@JsonProperty("hubs") 			val hubs 			: List<Hubs>?	 = ArrayList(),
	@JsonProperty("flows") 			val flows 			: List<Flows>? 	 = ArrayList(),
	@JsonProperty("textHtml") 		val text 			: String,
	@JsonProperty("tags") 			val tags 			: List<Tag>
)