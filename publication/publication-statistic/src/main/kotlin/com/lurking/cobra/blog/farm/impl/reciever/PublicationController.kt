package com.lurking.cobra.blog.farm.impl.reciever

import com.lurking.cobra.blog.farm.api.handler.PublicationHandler
import com.lurking.cobra.blog.farm.api.publication.model.PublicationDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/publication")
//@Profile(DEV_PROFILE)
class PublicationController(private val publicationHandler: PublicationHandler) {


    @PostMapping("/push")
    fun pushPublication(@RequestBody publicationDto: PublicationDto) {
        publicationHandler.handlePublication(publicationDto)
    }

}