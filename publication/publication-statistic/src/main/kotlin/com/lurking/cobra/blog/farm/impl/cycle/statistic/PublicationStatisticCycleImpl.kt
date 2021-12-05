package com.lurking.cobra.blog.farm.impl.cycle.statistic

import com.lurking.cobra.blog.farm.api.cycle.grow.GrowService
import com.lurking.cobra.blog.farm.api.cycle.selection.SelectionStrategy
import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticCycle
import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticManager
import com.lurking.cobra.blog.farm.api.cycle.statistic.StatisticService
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy
import com.lurking.cobra.blog.farm.impl.exception.ILLEGAL_EXCEPTION
import com.lurking.cobra.blog.farm.impl.exception.PublicationCycleRuntimeException
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class PublicationStatisticCycleImpl(
    private val selection: SelectionStrategy,
    private val grow: GrowService,
    private val statisticService: StatisticService
) : PublicationStatisticCycle {

    /**
     * Жизненный цикл публикации состоит из трех шагов:
     *
     * 1. сбор статистики - опрос источника публикации обновленной (или новой) статистики
     * по ресурсу, также могут участвовать сторонние анализаторы, для корректировки и добавления новых данных
     * о публикации
     *
     * 2. отбор публикаций - проставление (или изменение) одного из трех статусов публикации, в зависимости от результата
     *  определяются дальнейшие шаги
     *
     * 3. обработка статуса отбора:
     *  1. заморожена - публикация временно заморожена в подборе и сборе статистики (не рекомендована к публикации)
     *  2. рост - создается задание на обновление статистики по публикации (кандидат на добавление в список публикуемых)
     *  3. публикация - статья попадает в список публикуемых (рекомендована к публикации)
     *
     */
    override fun doPublicationCycle(publication: Publication, publisher: (Publication, PublicationStrategy) -> Unit) {
        logger.info { "Handler [log, urn = ${publication.urn}, uri = ${publication.uri} ] $publication" }
        // Шаги жизненного цикла
        lifecycle(publication, publisher)
    }

    private fun lifecycle(publication: Publication, publisher: (Publication, PublicationStrategy) -> Unit) {
        // 1. сбор статистики (предусмотреть внутри реализации статистик-менеджера для хабра развилку для новых и обновляемых публикаций)
        statisticService.enrich(publication)

        // 2. Отбор публикаций
        val strategy: PublicationStrategy = selection.selectPublicationStrategy(publication)

        // 3. Отправляем публикацию в сервис (сохраняем)
        publisher(publication, strategy)

        // 4. отправляем публикацию
        grow.attachPublication(publication, strategy)
    }

    companion object: KLogging()
}