package com.lurking.cobra.blog.generator.api.watcher.habr

/**
 * Данные о подписке (watcher'e) на разные типы каналов, блогов, компаний и тд
 */
data class Hub(
    // название подписки (на кого подписаны)
    private val name: String,
    // тип подписки (на что подписаны)
    private val type: HubType
)

