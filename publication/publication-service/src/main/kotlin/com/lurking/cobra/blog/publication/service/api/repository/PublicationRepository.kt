package com.lurking.cobra.blog.publication.service.api.repository

import com.lurking.cobra.blog.publication.service.api.model.entity.PublicationEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import com.lurking.cobra.blog.publication.service.api.model.entity.Status

@Repository
interface PublicationRepository : MongoRepository<PublicationEntity, String> {

    // max(rating) where status == PUBLICATION _READY && текущая_дата - месяц <= last_publication_timestamp limit count < .count
    // статус константы надо пофиксить по хорошему, выражение ${Status.READY_PUBLICATION} - не работает почему то
    //@Query("db.publication.find({'status': 2, 'last_publication': null}).sort({'rating':-1}).limit(?0)")
    //fun findPublicationThisMonth(count: Int): MutableList<PublicationEntity>

    //{$lte: new Date(ISODate().getTime() - 1000 * 60 * 60 * 24 * 30)}
    // статьи за последний год имеющие наименьшее кол-во публикаций
    //@Query("db.publication.find({'status': 2, 'last_publication': {\$lte:new Date(ISODate().getTime() - 1000 * 60 * 60 * 24 * 30),\$gt:new Date(ISODate().getTime() - 1000 * 60 * 60 * 24 * 180)}}).sort({'rating':-1, 'count_publication':1}).limit(?0)")
    //fun findPublicationThisYear(count: Int): MutableList<PublicationEntity>

    // инкриминируем count_publication
    //@Query("db.publication.update({ _id: ObjectId(\"?0\")}, {\$inc: {count_publication: 1}})")
    //@Query("db.publication.update({ _id: ObjectId(\"?0\")}, {\$inc: {count_publication: 1}})")
    //fun publicationEvent(id: String)

    // инкриминируем reactionName: count
    //@Query("db.publication.update({ _id: ObjectId(\"?0\")}, {\$inc: {\"reactions.?1\": 1}})")
    //fun reactionEvent(id: String, reactionName: String)
}