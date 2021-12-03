package com.lurking.cobra.blog.publication.service.impl.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.repository.PublicationRepository
import com.lurking.cobra.blog.publication.service.impl.listener.PublicationReactionListenerImpl
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

/**
 * Сервис, представляющий методы для работы с бизнес-логикой сервиса публикации.
 * Использует внедрение PublicationRepository для взаимодействия с базой данных.
 */
@Service
class ServicePublicationOrchestrationImpl @Autowired constructor(val publicationRepository: PublicationRepository,
                                                                 val converter: PublicationMapper) : ServicePublicationOrchestration {

    var logger: Logger = Logger.getLogger(PublicationReactionListenerImpl::class.java.toString())

    override fun findPublicationById(id: String): Publication {
        val entity = publicationRepository.findById(id).orElseThrow()
        return converter.convertEntityToModel(entity)
    }

    override fun createPublication(model: Publication): Publication {
        return savePublication(model)
    }

    override fun savePublication(model: Publication): Publication {
        // 1. Конвертируем модель в сущность
        val entity = converter.convertModelToEntity(model)

        // 2. Сохраняем в репозиторий
        publicationRepository.save(entity)

        return model
    }

    override fun publicationEvent(event: PublicationEvent) {
        logger.info("пришел объект $event")
        publicationRepository.publicationEvent(event.publicationId)
    }

    override fun reactionEvent(event: ReactionEvent) {
        publicationRepository.reactionEvent(event.publicationId, event.reaction)
    }

    override fun findMostActualPublications(count: Int): List<Publication> {
        // 1. не публиковавшиеся статьи этого месяца [топ приоритет]
        // max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp limit count < .count

        val publications: MutableList<PublicationEntity> = publicationRepository.findPublicationThisMonth(count)

        if (publications.size < count){

            var residue = count - publications.size

            // 2. max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp
            // max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp limit count < .count - loaded
            publications.addAll(publicationRepository.findPublicationThisYear(residue))


            if (publications.size < count) {
                // 3. [todo делаем позже] от 1 до 5 лет
                TODO()
            }
        }

        return publications.map { converter.convertEntityToModel(it) }
    }
}