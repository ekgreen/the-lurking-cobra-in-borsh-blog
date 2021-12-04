package com.lurking.cobra.blog.generator.impl.watcher.habr

import com.lurking.cobra.blog.generator.api.Generator
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.publication.PublicationProducer
import com.lurking.cobra.blog.generator.api.watcher.habr.HabrConfiguration
import com.lurking.cobra.blog.generator.api.watcher.habr.Hub
import com.lurking.cobra.blog.generator.api.watcher.habr.repository.HabrPublicationRepository
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.HabrVisitor
import com.lurking.cobra.blog.generator.api.watcher.habr.visitor.TailVisitorRule
import com.lurking.cobra.blog.generator.impl.watcher.habr.visitor.DayLimitTailVisitorRule
import com.lurking.cobra.blog.generator.impl.watcher.habr.visitor.JoinTailVisitorRule
import com.lurking.cobra.blog.generator.impl.watcher.habr.visitor.UntilPublicationDateTailVisitorRule

class HabrWatcher(
    private val configuration: HabrConfiguration,
    private val visitors: List<HabrVisitor>,
    private val repository: HabrPublicationRepository
) : Generator {

    override fun getPublications(): Iterator<PublicationProducer> {
        // 1. Взять из конфигурации "подписки" и итерироваться по ним (должен получится список итераторов)
        // 2. Передать подписки в итератор публикаций
        val subscriptions: List<HubSubscription> = configuration.subscriptions.map { subscriptionBuilder(it) }

        return HabrPublicationIterator( subscriptions, ::HabrPublicationProducer )
    }

    private inner class HabrPublicationProducer(private val callback: () -> Publication) : PublicationProducer {
        override fun produce(publisher: (Publication) -> Boolean) {
            // 1. Загрузим публикацию
            val publication: Publication = callback()
            // 2. Сохранить публикацию в лог как необработанную
            repository.save(publication)
            // 3. Вызываем блок кода потребителя (перехватывая исключения)
            val operationResult = publisher(publication)
            // 4. Если блок верну true - комитим в лог публикацию
            if (operationResult) repository.commit(publication)
        }
    }

    private fun subscriptionBuilder(hub: Hub): HubSubscription {
        // 1. Найти visitor, который обрабатывает hub
        val visitor: HabrVisitor = visitors.find { visitor -> visitor.isSupported(hub) }!!
        // реализовать механизм определения хвоста подписки по умолчанию
        val timeStrategy: TailVisitorRule = DayLimitTailVisitorRule(120)
        // 2. Получить последний вычитанный индекс статьи, мы знаем, что в блогах и прочих сущностях
        // все записи упорядочены по возрастанию
        // он должен полагаться на максимальный (последний) вычитанный индекс
        // последний индекс должен вычитаться единажды для одной подписки - чтобы не было коллизий
        val publicationTimestampStrategy: TailVisitorRule = UntilPublicationDateTailVisitorRule(repository.getLastPublicationTimestampBySubscriptionNameAndType(hub.name, hub.type))

        return HubSubscription(hub,visitor, JoinTailVisitorRule(listOf(timeStrategy,publicationTimestampStrategy)))
    }

    companion object {
        const val SOURCE_NAME = "urn:habr"
    }
}