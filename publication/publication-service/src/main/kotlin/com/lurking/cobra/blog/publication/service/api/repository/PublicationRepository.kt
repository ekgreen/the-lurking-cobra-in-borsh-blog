package com.lurking.cobra.blog.publication.service.api.repository

import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicationRepository : MongoRepository<PublicationEntity, String> {
}