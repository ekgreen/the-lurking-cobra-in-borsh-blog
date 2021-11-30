package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PublicationServiceController {
    @GetMapping("/post")
    fun getPublicationForPost(): PublicationDto {
        return PublicationDto()
    }

    @GetMapping("/publication")
    fun getPublicationById(id: Int): PublicationDto {
        return PublicationDto()
    }

    @PostMapping("/publication")
    fun createPublication(publicationDto: PublicationDto) {
    }

    @PatchMapping("/publication")
    fun updatePublication(publicationDto: PublicationDto) {
    }
}