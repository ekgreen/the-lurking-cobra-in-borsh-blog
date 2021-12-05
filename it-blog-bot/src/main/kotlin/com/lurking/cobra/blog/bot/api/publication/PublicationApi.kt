package com.lurking.cobra.blog.bot.api.publication


interface PublicationApi {

    companion object {
        private const val API_PATH  = "/publication-service/api/v1/publication"
        const val QUERY_SEARCH      = "$API_PATH/find/"
    }

    /**
     * Запрос в сервис "Публикаций" для получения наиболее популярных ресурсов для публикации
     *
     * @param count - кол-во запрашиваемых публикаций
     * @return публикация
     */
    fun getActualPosts(count: Int): List<Publication>
}