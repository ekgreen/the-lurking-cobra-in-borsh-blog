package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.service.PublicationValidatorService
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
                                                   @Qualifier("publicationValidatorService") val validator: PublicationValidatorService,
                                                   val converter: PublicationMapper) {

    /** Метод для получения наиболее актуальных статей */
    @GetMapping("/post/{count}")
    fun findPublicationsByQuery(@PathVariable("count") count: String): List<PublicationDto> {
        if (count.isEmpty())
            throw IllegalCallerException("Неверное значение count для получения наиболее актуальных публикаций")

        return orchestration.findMostActualPublications(count.toInt()).map { converter.convertModelToDto(it) }
    }

    /** Метод для поиска статьи по её id */
    @GetMapping("/publication/{id}")
    fun findPublicationById(@PathVariable("id") id: String): PublicationDto {
        if (id.isEmpty())
            throw IllegalCallerException("Неверное значение id для получения публикации")

        return orchestration.findPublicationById(id).let { converter.convertModelToDto(it) }
    }

    /** Метод создания статьи */
    @PostMapping("/publication")
    fun createPublication(publicationDto: PublicationDto) {
        validator.validateCreatePublication(publicationDto)
        converter.convertDtoToModel(publicationDto).let { orchestration.createPublication(it) }
    }

    /** Метод обновления данных о статье */
    @PatchMapping("/publication")
    fun updatePublication(publicationDto: PublicationDto) {
        validator.validateUpdatePublication(publicationDto)
        converter.convertDtoToModel(publicationDto).let { orchestration.savePublication(it) }
    }
}