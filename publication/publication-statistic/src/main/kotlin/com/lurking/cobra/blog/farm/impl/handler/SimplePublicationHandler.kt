package com.lurking.cobra.blog.farm.impl.handler

import com.lurking.cobra.blog.farm.api.cycle.statistic.PublicationStatisticCycle
import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.publication.*
import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.publisher.PublicationPublisher
import com.lurking.cobra.blog.farm.impl.exception.PublicationCycleRuntimeException
import com.lurking.cobra.blog.farm.impl.exception.VALIDATION_EXCEPTION
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class SimplePublicationHandler(
    private val statisticService: PublicationStatisticCycle,
    private val api: PublicationApi,
    private val publisher: PublicationPublisher,
) : PublicationHandler {

    /**
     * Failover Strategy
     * TODO failover strategy - продумать стратегию поведения системы при ошибках: какие могут быть, как обрабатывать
     */
    override fun handlePublication(request: PublicationDto) {
        logger.info { "Handler [log, urn = ${request.urn}, uri = ${request.uri} ] $request" }

        // 1. Валидация запроса на публикацию
        validateRequest(request)

        // 2. определить тип публикации новая или обновляемая
        // если у публикации нет идентификатора - новая, иначе - обновляемая
        // достаточно написать приватную реализацию PublicationCycleHolder и определить нужные методы
        // 3. Обновить данные по статье из PublicationServiceApi если публикация "обновляемая"
        val entry: Publication = findOrCreatePublication(request)

        // 4. Проведем операции связанные с ЖЦ публикации
        statisticService.doPublicationCycle(entry) { publication, strategy ->
            // 5. Проставим стратегию публикации
            publication.strategy = strategy
            // 6. Отбросить сообщение в сервис
            publisher.publish(publication, strategy)
        }
    }

    private fun validateRequest(request: PublicationDto) {
        if (request.id == null && (request.uri == null || request.urn == null))
            throw PublicationCycleRuntimeException(VALIDATION_EXCEPTION, "invalid statistic publication request")
    }

    private fun findOrCreatePublication(request: PublicationDto): Publication {
        // 1. Если у публикации есть уникальный идентификатор - обновляемая, пробуем найти в АПИ
        // 2. Иначе - новая
        return if (request.id != null) api.findById(request.id!!) else createPublication(request)
    }

    private fun createPublication(request: PublicationDto): Publication {
        return Publication(uri = request.uri!!, urn = request.urn!!)
    }

    companion object: KLogging()
}