package com.lurking.cobra.blog.publication.service.impl.service

import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import com.lurking.cobra.blog.publication.service.api.service.PublicationValidatorService
import org.springframework.stereotype.Service

@Service
class PublicationValidatorServiceImpl : PublicationValidatorService {
    override fun validateCreatePublication(publicationDto: PublicationDto) {
        if (publicationDto.id != null || publicationDto.uri.isEmpty() || publicationDto.title.isEmpty()) {
            throw IllegalStateException("Для создания сущности переданы неверные значения данных в PublicationDto")
        }
    }

    override fun validateUpdatePublication(publicationDto: PublicationDto) {
        if (publicationDto.id == null || publicationDto.uri.isEmpty() || publicationDto.title.isEmpty()) {
            throw IllegalStateException("Для обновления сущности переданы неверные значения данных в PublicationDto")
        }
    }
}