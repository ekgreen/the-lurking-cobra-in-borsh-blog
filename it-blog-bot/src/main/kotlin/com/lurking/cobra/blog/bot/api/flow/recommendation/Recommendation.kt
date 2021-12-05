package com.lurking.cobra.blog.bot.api.flow.recommendation

data class Recommendation (
    var source: PublicationSource? = null,
    var link: String? = null,
    var title: String? = null,
    var description: String? = null,
)