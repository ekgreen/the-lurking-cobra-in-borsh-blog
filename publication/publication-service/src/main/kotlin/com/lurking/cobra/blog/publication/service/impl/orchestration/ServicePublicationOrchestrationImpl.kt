package com.lurking.cobra.blog.publication.service.impl.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.PublicationEvent
import com.lurking.cobra.blog.publication.service.api.model.PublicationStrategy
import com.lurking.cobra.blog.publication.service.api.model.dto.ReactionEvent
import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.repository.PublicationRepository
import com.lurking.cobra.blog.publication.service.impl.listener.PublicationReactionListenerImpl
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.*
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Logger


/**
 * Сервис, представляющий методы для работы с бизнес-логикой сервиса публикации.
 * Использует внедрение PublicationRepository для взаимодействия с базой данных.
 */
@Service
class ServicePublicationOrchestrationImpl(
    private val publicationRepository: PublicationRepository,
    private val converter: PublicationMapper,
    private val mongoTemplate: MongoTemplate
) : ServicePublicationOrchestration {

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
        return publicationRepository.save(entity).let { converter.convertEntityToModel(it) }
    }

    override fun publicationEvent(event: PublicationEvent) {
        logger.info("пришел объект $event")

        val publicationEntity = publicationRepository.findById(event.publicationId).orElseThrow()

        publicationEntity.publicationsCount     += 1
        publicationEntity.lastPublicationDate   = Date()

        publicationRepository.save(publicationEntity)
    }

    override fun reactionEvent(event: ReactionEvent) {

        val publicationEntity: PublicationEntity = publicationRepository.findById(event.publicationId).orElseThrow()

        publicationEntity.reactions.handleReaction(event.reaction, event.count)
        publicationEntity.reactionRating = (publicationEntity.reactions.likesCount + 2* publicationEntity.reactions.lightingCount - publicationEntity.reactions.pokerFaceCount).toDouble() / (publicationEntity.reactions.likesCount + publicationEntity.reactions.lightingCount + publicationEntity.reactions.pokerFaceCount).toDouble()

        publicationRepository.save(publicationEntity)
    }

    override fun findMostActualPublications(count: Int): List<Publication> {
        // 1. не публиковавшиеся статьи этого месяца [топ приоритет]

        var statusCriteria: Criteria = Criteria.where("strategy").`is`(PublicationStrategy.PUBLISHING)
        var existsCriteria: Criteria = Criteria.where("last_publication").exists(false)

        var resultCriteria: Criteria = Criteria().andOperator(statusCriteria, existsCriteria)

        val firstBatch: Query = Query(resultCriteria)
            .with(Sort.by(Sort.Direction.DESC, "rating")).limit(count)

        val publications: MutableList<PublicationEntity> = mongoTemplate.find(firstBatch, PublicationEntity::class.java, "publication")

        if (publications.size < count) {

            val residue = count - publications.size

            val dateFrom = getDaysAgo(180)
            val dateTo = getDaysAgo(30)

            statusCriteria = Criteria.where("strategy").`is`(PublicationStrategy.PUBLISHING)
            existsCriteria = Criteria.where("last_publication").gte(dateFrom).lt(dateTo)
            resultCriteria = Criteria().andOperator(statusCriteria, existsCriteria)

            val secondBatch: Query = Query(resultCriteria)
                .with(Sort.by(Sort.Direction.DESC, "rating")).limit(residue)

            publications.addAll(mongoTemplate.find(secondBatch, PublicationEntity::class.java, "publication"))

            // 3. [todo делаем позже] от 1 до 5 лет
        }

        return publications.map { converter.convertEntityToModel(it) }
    }

    override fun createDigest(count: Int): List<Publication> {
        val weeklyCriteria: Criteria = Criteria.where("last_publication").gte(LocalDateTime.now().minusDays(7)).lte(LocalDateTime.now())
        val reactionCriteria: Criteria = Criteria.where("reaction_rating").gte(0.5)

        val query: Query = Query(Criteria().andOperator(reactionCriteria, weeklyCriteria))
            .with(Sort.by(Sort.Direction.DESC, "reaction_rating")).limit(count)

        val publications = mongoTemplate.find(query, PublicationEntity::class.java, "publication")

        return publications.map { converter.convertEntityToModel(it) }
    }

    override fun findPublicationsByTags(tags: Set<String>, count: Int): List<Publication> {

        // выбираем статьи растущие
        val statusGrowCriteria: Criteria= Criteria("strategy").`is`(PublicationStrategy.GROW)
        // выбираем статьи готовые к публикации
        val statusPublishingCriteria: Criteria= Criteria("strategy").`is`(PublicationStrategy.PUBLISHING)
        // в результате публикации должны быть либо растущие, либо готовые к публикации
        val statusCriteria: Criteria = Criteria().orOperator(statusGrowCriteria, statusPublishingCriteria)

        // выбираем статьи по тегам
        val tagsCriteria: Criteria = Criteria().andOperator( tags.map { Criteria.where("tags").inValues(it) })

        // соединяем запрос statusCriteria и tagsCriteria логическим И
        val resultCriteria: Criteria = Criteria().andOperator(tagsCriteria, statusCriteria)

        val publicationsBatch: Query = Query(resultCriteria)
            .with(Sort.by(Sort.Direction.DESC, "rating")).limit(count)

        val publications: MutableList<PublicationEntity> = mongoTemplate.find(publicationsBatch, PublicationEntity::class.java, "publication")

        return publications.map { converter.convertEntityToModel(it) }
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
}