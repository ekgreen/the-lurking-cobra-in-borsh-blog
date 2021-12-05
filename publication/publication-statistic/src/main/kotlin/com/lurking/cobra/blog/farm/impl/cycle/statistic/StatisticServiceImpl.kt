package com.lurking.cobra.blog.farm.impl.cycle.statistic

import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticManager
import com.lurking.cobra.blog.farm.api.cycle.statistic.StatisticService
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.rating.PublishingRatingService
import com.lurking.cobra.blog.farm.impl.exception.ILLEGAL_EXCEPTION
import com.lurking.cobra.blog.farm.impl.exception.PublicationCycleRuntimeException
import org.springframework.stereotype.Service

@Service
class StatisticServiceImpl(
    private val ratingService: PublishingRatingService,
    private val statisticManagers: List<PublicationStatisticManager>
) : StatisticService {

    override fun enrich(publication: Publication) {
        // 1. Найдем сервис статистика для данного типа публикации
        val statistic: PublicationStatisticManager = selectStatisticManager(publication)
            ?: throw PublicationCycleRuntimeException(ILLEGAL_EXCEPTION, "сервис по сбору статистики для публикации не { urn = ${publication.urn} uri = ${publication.uri} }")

        // 2. Обогатим или обновим данными публикацию
        statistic.enrich(publication)

        // 3. Высчитаем и обновим рейтинг публикации
        publication.rating = ratingService.publishingRate(publication)
    }

    private fun selectStatisticManager(publication: Publication): PublicationStatisticManager? {
        return statisticManagers.find { it.isSupported(publication.urn) }
    }
}