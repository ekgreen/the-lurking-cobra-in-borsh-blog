package com.lurking.cobra.blog.generator.impl

import com.lurking.cobra.blog.generator.api.GeneratorLog
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

open class JdbcGeneratorLog(private val jdbcTemplate: NamedParameterJdbcTemplate): GeneratorLog {

    @Transactional
    override fun write(generatorId: Long, entityId: Long): Long {
        val holder: KeyHolder = GeneratedKeyHolder()

        val select: String = """
            insert into generator.generator_log(generator_id, entity_id) VALUES (:generatorId, :habrLogId);
        """.trimIndent()

        val parameters = MapSqlParameterSource()
            .addValue("generatorId", generatorId)
            .addValue("habrLogId", entityId)

        jdbcTemplate.update(select, parameters, holder, arrayOf( "id" ))
        return holder.key?.toLong()  ?: throw RuntimeException("failed write in generators log!")
    }

    override fun commit(generatorLogId: Long) {
        val select: String = """
            update generator.generator_log set executed = :executed where id = :generatorLogId
        """.trimIndent()

        val parameters = MapSqlParameterSource()
            .addValue("executed", true)
            .addValue("generatorLogId", generatorLogId)

        jdbcTemplate.update(select, parameters)
    }
}