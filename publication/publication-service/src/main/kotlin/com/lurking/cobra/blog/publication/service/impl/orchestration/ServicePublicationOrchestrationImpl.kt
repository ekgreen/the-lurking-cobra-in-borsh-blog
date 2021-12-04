package com.lurking.cobra.blog.publication.service.impl.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import com.lurking.cobra.blog.publication.service.api.model.entity.Status
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.repository.PublicationRepository
import com.lurking.cobra.blog.publication.service.impl.listener.PublicationReactionListenerImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger


/**
 * Сервис, представляющий методы для работы с бизнес-логикой сервиса публикации.
 * Использует внедрение PublicationRepository для взаимодействия с базой данных.
 */
@Service
class ServicePublicationOrchestrationImpl @Autowired constructor(val publicationRepository: PublicationRepository,
                                                                 val converter: PublicationMapper,
                                                                 val mongoTemplate: MongoTemplate) : ServicePublicationOrchestration {

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

        val publicationEntity = publicationRepository.findById(event.publicationId).orElseThrow()
        publicationEntity.publication_count++
        publicationRepository.save(publicationEntity)
    }

    override fun reactionEvent(event: ReactionEvent) {

        val publicationEntity = publicationRepository.findById(event.publicationId).orElseThrow()
        var count = publicationEntity.reactions[event.reaction]

        if (count != null) {
            publicationEntity.reactions[event.reaction] = ++count
        }

        publicationRepository.save(publicationEntity)
    }

    override fun findMostActualPublications(count: Int): List<Publication> {
        // 1. не публиковавшиеся статьи этого месяца [топ приоритет]

        var c1: Criteria = Criteria.where("status").`is`(Status.PUBLICATION_READY.ordinal)
        var c2: Criteria = Criteria.where("last_publication").exists(false)
        var resultCriteria: Criteria = Criteria().andOperator(c1, c2)

        val query1: Query = Query(resultCriteria)
            .with(Sort.by(Sort.Direction.DESC, "rating")).limit(count)

        val publications = mongoTemplate.find(query1, PublicationEntity::class.java, "publication")

        if (publications.size < count){

            var residue = count - publications.size

            val dateFrom = getDaysAgo(180)
            val dateTo = getDaysAgo(30)

            c1 = Criteria.where("status").`is`(Status.PUBLICATION_READY.ordinal)
            c2 = Criteria.where("last_publication").gte(dateFrom).lt(dateTo)
            resultCriteria = Criteria().andOperator(c1, c2)

            val query2: Query = Query(resultCriteria)
                .with(Sort.by(Sort.Direction.DESC, "rating")).limit(residue)

            publications.addAll(mongoTemplate.find(query2, PublicationEntity::class.java ,"publication"))


            if (publications.size < count) {
                // 3. [todo делаем позже] от 1 до 5 лет
                TODO()
            }
        }

        return publications.map { converter.convertEntityToModel(it) }
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
}