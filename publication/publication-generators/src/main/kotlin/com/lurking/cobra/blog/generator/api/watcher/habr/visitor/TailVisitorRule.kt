package com.lurking.cobra.blog.generator.api.watcher.habr.visitor

import com.lurking.cobra.blog.generator.api.publication.Publication

interface TailVisitorRule {

    /**
     * Правило определяет хвостовую публикацию
     * Хвостовая публикация - та после которой записи считаются не актуальными к прочтению
     *
     * @param publication публикация
     * @return признак хвостовой публикации
     */
    fun isTailPublication(publication: Publication) : Boolean
}