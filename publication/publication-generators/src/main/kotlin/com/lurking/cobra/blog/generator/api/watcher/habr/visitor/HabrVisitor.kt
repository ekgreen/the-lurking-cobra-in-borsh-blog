package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.publication.Publication

interface HabrVisitor {

    /**
     * Посетить хаб для поиска новых статей
     *
     * @param hub данные о хабе который хотим посетить
     * @param rule правило хвостового поста
     *
     * @return список публикаций
     */
    fun visitHub(hub: Hub, rule: TailVisitorRule) : Iterator<Publication>

    /**
     * Метод определяет, какие хабы поддерживаются визитором
     */
    fun isSupported(hub: Hub) : Boolean
}