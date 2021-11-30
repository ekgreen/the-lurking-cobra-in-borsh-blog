package com.lurking.cobra.blog.publication.service.api.model

import com.lurking.cobra.blog.publication.service.api.model.entity.Status


class Publication(
    var id: String? = null,
    var uri: String,
    var title: String,
    var rating: Int,
    var tags: MutableSet<String> = mutableSetOf(),
    var key_words: MutableSet<String> = mutableSetOf(),
    var reactions: MutableMap<String, Int> = mutableMapOf(),
    var status: Enum<Status>
) {

}