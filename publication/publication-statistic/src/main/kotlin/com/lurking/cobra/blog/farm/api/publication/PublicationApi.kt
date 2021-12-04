package com.lurking.cobra.blog.farm.api.publication

import com.lurking.cobra.blog.farm.api.publication.model.Publication


interface PublicationApi {

    companion object {
        private const val API_PATH  = "/publication-service/api/v1"
        const val VIEW_BY_ID_PATH   = "$API_PATH/view/"
        const val ADD_BY_ID_PATH    = "$API_PATH/add"
        const val EDIT_BY_ID_PATH   = "$API_PATH/edit/"
    }

    /**
     * Запрос в сервис "Публикаций" для получения ресурса по идентификатору
     *
     * @param id - уникальный идентификатор публикации
     * @return публикация
     */
    fun findById(id: String): Publication

    /**
     * Сохранить ресурс в сервисе "Публикаций"
     *
     * @param publication публикация
     * @return уникальный идентификатор публикации
     */
    fun savePublication(publication: Publication): Publication
}