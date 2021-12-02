package com.lurking.cobra.blog.farm.api.handler

interface PublicationHandler {

    /**
     * Метод для синхронной обработки публикаций
     *
     * @param request - публикация
     */
    fun handlePublication(request: PublicationRequest)
}