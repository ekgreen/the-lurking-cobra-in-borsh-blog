package com.lurking.cobra.blog.generator.impl

import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.api.Generator


/**
 * Общий класс генерации публикаций (aka ресурсов)
 *
 * Генерация публикаций - процесс поиска новых ресурсов в сети интернет
 */
class PublicationGenerator(
    private val generators: List<Generator>,
    private val publisher: PublicationPublisher
    ) {

    /**
     * Метод итерируется по всем доступным генераторам
     * и запускает их метод {@link Generator#getPublications(...)}
     */
    fun process() {
        // 1. Итерируемся по генераторам
        // 2. Вызвать у каждого метод getPublications
        // 3. Проитерироваться последовательно публикую статьи в паблишер
    }
}