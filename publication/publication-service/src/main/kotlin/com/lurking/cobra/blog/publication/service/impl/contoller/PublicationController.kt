package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.PublicationReactions
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import com.lurking.cobra.blog.publication.service.api.service.PublicationValidatorService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.Month

/**
 * Контроллер сервиса публикаций, предоставляющий методы для взаимодействия с другими сервисами.
 * Использует внедрение оркестратора для взаимодействия с бизнес-логикой сервиса публикаций.
 * Использует внедрение сервиса-валидации получаемых PublicationDto - сущностей.
 */
@RestController
@RequestMapping("/api/v1/publication")
class PublicationController(
    val orchestration: ServicePublicationOrchestration,
    val validator: PublicationValidatorService,
    val converter: PublicationMapper
) {

    /** Метод для получения наиболее актуальных статей */
    @GetMapping("/find/{count}")
    fun findPublicationsByQuery(@PathVariable("count") count: String): List<PublicationDto> {
        if (count.isEmpty())
            throw IllegalCallerException("Неверное значение count для получения наиболее актуальных публикаций")

        return orchestration.findMostActualPublications(count.toInt()).map { converter.convertModelToDto(it) }
    }

    /** Метод для получения наиболее актуальных статей */
    @GetMapping("/publications_by_tags")
    fun getPublicationsByQuery(@RequestBody request: PublicationQueryRequest): List<PublicationDto> {
        return orchestration.findPublicationsByTags(request.tags!!, request.count).map { converter.convertModelToDto(it) }
    }

    /** Метод для получения наиболее актуальных статей */
    @GetMapping("/most_popular_publications")
    fun getMostPopularPublications(): List<PublicationDto> {
        return orchestration.findMostPopularPublications(LocalDateTime.of(2021, Month.DECEMBER, 1, 10, 10, 10),
                                                         LocalDateTime.of(2021, Month.JULY, 1, 10, 10, 10)).map { converter.convertModelToDto(it) }
    }

    /** Метод для поиска статьи по её id */
    @GetMapping("/view/{id}")
    fun findPublicationById(@PathVariable("id") id: String): PublicationDto {
        if (id.isEmpty())
            throw IllegalCallerException("Неверное значение id для получения публикации")

        return orchestration.findPublicationById(id).let { converter.convertModelToDto(it) }
    }

    /** Метод создания статьи */
    @PostMapping("/add")
    fun createPublication(@RequestBody publicationDto: PublicationDto): PublicationDto {
        validator.validateCreatePublication(publicationDto)
        return orchestration.savePublication(publicationDto.let { converter.convertDtoToModel(enrichRequestDto(it)) })
            .let { converter.convertModelToDto(it)  }
    }

    /** Метод обновления данных о статье */
    @PostMapping("/edit/{id}")
    fun updatePublication(@RequestBody publicationDto: PublicationDto) : PublicationDto {
        validator.validateUpdatePublication(publicationDto)
        return orchestration.savePublication(publicationDto.let { converter.convertDtoToModel(enrichRequestDto(it)) })
            .let { converter.convertModelToDto(it)  }
    }

    private fun enrichRequestDto(publicationDto: PublicationDto): PublicationDto {
        publicationDto.keywords = publicationDto.keywords?: mutableSetOf()
        publicationDto.reactions = publicationDto.reactions?: PublicationReactions()
        publicationDto.publicationsCount = publicationDto.publicationsCount?: 0
        return publicationDto
    }
}