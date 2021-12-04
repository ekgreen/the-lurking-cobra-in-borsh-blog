package com.lurking.cobra.blog.generator.impl

import com.lurking.cobra.blog.generator.api.publication.PublicationPublisher
import com.lurking.cobra.blog.generator.api.Generator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


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
    @Scheduled(fixedDelay = 1000 * 10)
    fun process() {
        // 1. Итерируемся по генераторам
        generators.forEach {
            // 2. Вызвать у каждого метод getPublications
            it.getPublications().forEach { publicationProducer ->
                publicationProducer.produce { publication ->
                    // 3. Проитерироваться последовательно публикую статьи в паблишер
                    publisher.publish(publication)
                    // 4. Признак успешно обработанной публикации
                    return@produce true
                }
            }
        }
    }
}