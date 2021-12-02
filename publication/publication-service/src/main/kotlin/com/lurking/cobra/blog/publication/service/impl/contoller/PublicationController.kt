package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.service.PublicationValidatorService
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * Контроллер сервиса публикаций, предоставляющий методы для взаимодействия с другими сервисами.
 * Использует внедрение оркестратора для взаимодействия с бизнес-логикой сервиса публикаций.
 * Использует внедрение сервиса-валидации получаемых PublicationDto - сущностей.
 */
@RestController
class PublicationController @Autowired constructor(val orchestration: ServicePublicationOrchestration,
                                                   @Qualifier("publicationValidatorService") val validator: PublicationValidatorService) {

    private val converter = Mappers.getMapper(PublicationMapper::class.java)

    /** Метод для получения наиболее актуальных статей */
    @GetMapping("/post/{count}")
    fun findPublicationsByQuery(@PathVariable("count") count: String): List<PublicationDto> {
        if (count.isNullOrEmpty()) {
            throw IllegalCallerException("Неверное значение count для получения наиболее актуальных публикаций")
        }

        val response = mutableListOf<PublicationDto>()
        val mostActualPublication = orchestration.findMostActualPublications(count.toInt())

        for (publication in mostActualPublication) {
            response.add(converter.convertModelToDto(publication))
        }
        return response
    }

    /** Метод для поиска статьи по её id */
    @GetMapping("/publication/{id}")
    fun findPublicationById(@PathVariable("id") id: String): PublicationDto {
        if (id.isNullOrEmpty()) {
            throw IllegalCallerException("Неверное значение id для получения публикации")
        }

        val publicationModel = orchestration.findPublicationById(id)
        return converter.convertModelToDto(publicationModel)
    }

    /** Метод создания статьи */
    @PostMapping("/publication")
    fun createPublication(publicationDto: PublicationDto) {
        validator.validateCreatePublication(publicationDto)

        val model: Publication = converter.convertDtoToModel(publicationDto)
        orchestration.createPublication(model)
    }

    /** Метод обновления данных о статье */
    @PatchMapping("/publication")
    fun updatePublication(publicationDto: PublicationDto) {
        validator.validateUpdatePublication(publicationDto)

        val model: Publication = converter.convertDtoToModel(publicationDto)
        orchestration.savePublication(model)
    }
}