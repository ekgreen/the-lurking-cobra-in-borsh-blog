package com.lurking.cobra.blog.publication.service.impl.orchestration

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.repository.PublicationRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import java.time.temporal.Temporal

@Service
class ServicePublicationOrchestrationImpl(private val publicationRepository: PublicationRepository) : ServicePublicationOrchestration {

    private val converter = Mappers.getMapper(PublicationMapper::class.java)

    override fun findPublicationById(id: String): Publication {
        val entity = publicationRepository.findById(id).orElseThrow()
        return converter.convertEntityToModel(entity)
    }

    override fun createPublication(model: Publication): Publication {
        if(model.id != null)
            throw IllegalStateException("")

        return savePublication(model)
    }

    override fun savePublication(model: Publication): Publication {
        // 1. Конвертируем модель в сущность
        val entity = converter.convertModelToEntity(model)

        // 2. Сохраняем в репозиторий
        publicationRepository.save(entity)
        Temporal
        return  model
    }

    override fun findMostActualPublications(count: Int): List<Publication> {
        // 1. не публиковавшиеся статьи этого месяца [топ приоритет]
        // max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp limit count < .count


        // 2. max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp
        // max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp limit count < .count - loaded

        // 3. [todo делаем позже] от 1 до 5 лет

        TODO()
    }
}