package com.lurking.cobra.blog.generator.impl.watcher.habr.repository

import com.lurking.cobra.blog.generator.api.GeneratorLog
import com.lurking.cobra.blog.generator.api.publication.Publication
import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import com.lurking.cobra.blog.generator.api.watcher.habr.repository.HabrPublicationRepository
import com.lurking.cobra.blog.generator.impl.watcher.habr.HabrWatcher.Companion.SOURCE_NAME
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.Delegates


open class JdbcHabrPublicationRepository(private val namedTemplate: NamedParameterJdbcTemplate, private val generatorLog: GeneratorLog) : HabrPublicationRepository {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private @Volatile var generatorId: Long? = null
    // todo переписать на loading cache с ttl (при ошибках будет забиваться память)
    private val commitMap: MutableMap<String, Long> = ConcurrentHashMap()


    private fun registryGenerator(): Long {
        val holder: KeyHolder = GeneratedKeyHolder()
        val parameters = MapSqlParameterSource()
            .addValue("type", SOURCE_NAME)
        namedTemplate.update("insert into generator.generator_type (type) VALUES (:type)", parameters, holder, arrayOf("id"))
        return holder.key?.toLong() ?: throw RuntimeException("failed registry habr generator type!")
    }

    override fun getLastPublicationTimestampBySubscriptionNameAndType(subscriptionName: String, subscriptionType: HubType): LocalDateTime? {
        val select: String = """
            select publication_timestamp 
                from generator.generator_log l 
                inner join
                ( select id, publication_timestamp 
                    from generator.habr_log_entity 
                    where name = :name and type = :type 
                    order by publication_timestamp DESC 
                    limit 1
                ) e
                on l.entity_id = e.id
        """.trimIndent()

        val parameters = MapSqlParameterSource()
            .addValue("name", subscriptionName)
            .addValue("type", subscriptionType.name)

        val query: List<String> = namedTemplate.query(select, parameters) { rs, _ -> rs.getString(1) }

        return if(query.isEmpty()) null else LocalDateTime.parse(query[0], formatter)
    }

    @Transactional
    override fun save(publication: Publication) {
        // 1. Сохраним запись в лог Habr
        val entityLogId: Long = saveHabrEntity(publication)
        // 2. Сохраним запись в лог Generator
        val generatorId: Long = generatorId()
        val generatorLogId = generatorLog.write(generatorId, entityLogId)
        // 3. Сохраним запись для комита
        commitMap[publication.uri] = generatorLogId
    }

    private fun generatorId(): Long {
        var generatorId: Long? = null

        if(this.generatorId == null) {
            synchronized(this) {
                if (this.generatorId == null) {
                    val ids: List<Long> = namedTemplate.jdbcTemplate.query(
                        "select id from generator.generator_type where type = '$SOURCE_NAME'",
                    ) { rs, _ -> rs.getLong(1) }

                    generatorId = if(ids.isEmpty()) registryGenerator() else ids[0]
                }
            }
        }

        return generatorId!!
    }

    private fun saveHabrEntity(publication: Publication): Long {
        val holder: KeyHolder = GeneratedKeyHolder()

        val select: String = """
            insert into generator.habr_log_entity(page_id, publication_timestamp, name, type) VALUES (:pageId, :publicationTimestamp, :name, :type)
        """.trimIndent()
        val parameters = MapSqlParameterSource()
            .addValue("pageId", publication.uri)
            .addValue("publicationTimestamp", publication.timestamp)
            .addValue("name", publication.hub.name)
            .addValue("type", publication.hub.type.name)

        namedTemplate.update(select, parameters, holder, arrayOf("id"))

        return holder.key?.toLong() ?: throw RuntimeException("habr entity log insertion failed!")
    }

    override fun commit(publication: Publication) {
        // 1. Получим запись для комита
        val generatorLogId = commitMap[publication.uri]?:
            throw RuntimeException("publication {${publication.uri}} was not write on generator's log!")

        // 2. Закомитим запись
        generatorLog.commit(generatorLogId)

        // 3. Удалим из коммит стора данные
        commitMap.remove(publication.uri)
    }
}