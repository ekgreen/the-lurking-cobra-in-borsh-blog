package com.lurking.cobra.blog.generator.api.watcher.habr

import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Данные о подписке (watcher'e) на разные типы каналов, блогов, компаний и тд
 */
data class Hub @ConstructorBinding constructor(
    // название подписки (на кого подписаны)
    val name: String,
    // тип подписки (на что подписаны)
    val type: HubType
)

