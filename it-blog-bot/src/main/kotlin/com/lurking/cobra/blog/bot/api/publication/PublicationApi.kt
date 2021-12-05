package com.lurking.cobra.blog.bot.api.publication


interface PublicationApi {

    companion object {
        private const val API_PATH  = "/publication-service/api/v1/publication"
        const val FIND_SEARCH      = "$API_PATH/find/"
        const val QUERY_SEARCH      = "$API_PATH/query"
        const val GET_DIGEST      = "$API_PATH/digest/"
    }

    /**
     * Запрос в сервис "Публикаций" для получения наиболее популярных ресурсов для публикации
     *
     * @param count - кол-во запрашиваемых публикаций
     * @return публикации
     */
    fun getActualPosts(count: Int): List<Publication>

    /**
     * Запрос в сервис "Публикаций" для получения наиболее популярных ресурсов по тегам
     *
     * @param tags - тэги для запроса
     * @param count - кол-во запрашиваемых публикаций

     * @return публикации
     */
    fun findPostByTags(tags: Set<String>, count: Int): List<Publication>

    /**
     * Запрос в сервис "Публикаций" для получения недельного дайджеста
     *
     * @param count - кол-во запрашиваемых публикаций

     * @return публикации
     */
    fun getDigest(count: Int): List<Publication>
}