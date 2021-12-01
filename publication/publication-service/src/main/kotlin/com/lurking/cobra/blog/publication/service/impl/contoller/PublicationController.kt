package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.Publication
import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.model.mapper.PublicationMapper
import com.lurking.cobra.blog.publication.service.api.orchestration.ServicePublicationOrchestration
import org.springframework.web.bind.annotation.*


@RestController
class PublicationController(
    private val orchestration: ServicePublicationOrchestration,
    private val mapper: PublicationMapper
) {

    @GetMapping("/post")
    fun findPublicationsByQuery(): List<PublicationDto> {
        return PublicationDto()
    }

    @GetMapping("/publication/{id}")
    fun findPublicationById(@PathVariable("id") id: String): PublicationDto {
        return PublicationDto()
    }

    @PostMapping("/publication")
    fun createPublication(publicationDto: PublicationDto) {
        // 1. валидация публикации от внешнего сервиса

        // 2. перемапить из дто в модель
        val model: Publication = mapper.convertDtoToModel(publicationDto)

        // 3. отправляем данные в оркестровщик
        orchestration.createPublication(model)
    }

    @PatchMapping("/publication")
    fun updatePublication(publicationDto: PublicationDto) {
    }
}