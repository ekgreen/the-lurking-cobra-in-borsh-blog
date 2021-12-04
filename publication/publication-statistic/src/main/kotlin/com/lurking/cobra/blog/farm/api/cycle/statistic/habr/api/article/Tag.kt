package com.lurking.cobra.blog.farm.api.cycle.statistic.habr.api.article

import com.fasterxml.jackson.annotation.JsonProperty

data class Tag (
	@JsonProperty("titleHtml") val value: String
)