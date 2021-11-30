package com.lurking.cobra.blog.generator.impl.watcher.habr

import com.lurking.cobra.blog.generator.api.Generator
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationProducer
import com.lurking.cobra.blog.generator.api.watcher.habr.HabrConfiguration
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.repository.HabrPublicationRepository
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.HabrVisitor

class HabrWatcher(
    private val configuration: HabrConfiguration,
    private val visitors: List<HabrVisitor>,
    private val repository: HabrPublicationRepository
) : Generator {

    override fun getPublications(): Iterator<PublicationProducer> {
        // 1. Взять из конфигурации "подписки" и итерироваться по ним (должен получится список итераторов)

        // 2. Передать подписки в итератор публикаций

        return HabrPublicationIterator( /* todo */ ArrayList(), ::HabrPublicationProducer)
    }


    private inner class HabrPublicationProducer(private val publication: Publication) : PublicationProducer {
        override fun produce(publisher: (Publication) -> Boolean) {
            // 1. Сохранить публикацию в лог как полученное
            repository.save(publication)
            // 2. Вызываем блок кода потребителя (перехватывая исключения)
            val operationResult = publisher(publication)
            // 3. Если блок верну true - комитим в лог публикацию
            if (operationResult) repository.commit(publication)
        }
    }

    private fun subscriptionBuilder(hub: Hub): HubSubscription {
        // 1. Найти visitor, который обрабатывает hub

        // реализовать механизм определения хвоста подписки по умолчанию

        // 2. Получить последний вычитанный индекс статьи, мы знаем, что в блогах и прочих сущностях
        // все записи упорядочены по возрастанию

        // он должен полагаться на максимальный (последний) вычитанный индекс
        // последний индекс должен вычитаться единажды для одной подписки - чтобы не было коллизий

        TODO()
    }

}