package com.lurking.cobra.blog.farm.api.publication


interface PublicationApi {

    /**
     * Запрос в сервис "Публикаций" для получения ресурса по идентификатору
     *
     * @param id - уникальный идентификатор публикации
     * @return публикация
     */
    fun findById(id: String): Publication
}