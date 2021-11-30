package com.lurking.cobra.blog.publication.service.impl.contoller

import com.lurking.cobra.blog.publication.service.api.model.dto.PublicationDto
import org.springframework.web.bind.annotation.*


@RestController
class PublicationServiceController {
    @GetMapping("/post")
    fun getPublicationForPost(): PublicationDto {
        return PublicationDto()
    }

    @GetMapping("/publication/{id}")
    fun getPublicationById(@PathVariable("id") id: Int): PublicationDto {
        return PublicationDto()
    }

    @PostMapping("/publication")
    fun createPublication(publicationDto: PublicationDto) {
    }

    @PatchMapping("/publication")
    fun updatePublication(publicationDto: PublicationDto) {
    }
}