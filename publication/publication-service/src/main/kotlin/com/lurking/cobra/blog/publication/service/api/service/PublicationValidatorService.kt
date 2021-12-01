package com.lurking.cobra.blog.publication.service.api.service

import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import org.springframework.stereotype.Service

/**
 * Интерфейс, предоставляющий методы для валидации PublicationDto - сущности на уровне контроллеров
 */
@Service
interface PublicationValidatorService {

    fun validateCreatePublication(publicationDto: PublicationDto)

    fun validateUpdatePublication(publicationDto: PublicationDto)
}