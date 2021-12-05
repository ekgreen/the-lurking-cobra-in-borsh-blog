package com.lurking.cobra.blog.generator.api

interface GeneratorLog {

    fun write(generatorId: Long, entityId: Long): Long

    fun commit(generatorLogId: Long)
}