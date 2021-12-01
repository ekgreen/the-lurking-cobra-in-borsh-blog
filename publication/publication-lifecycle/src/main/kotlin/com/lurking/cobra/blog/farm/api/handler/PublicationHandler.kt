package com.lurking.cobra.blog.farm.api.handler

import com.lurking.cobra.blog.farm.api.reciever.PublicationRequest

interface PublicationHandler {

    /**
     * Метод для синхронной обработки публикаций
     *
     * @param request - публикация
     */
    fun handlePublication(request: PublicationRequest)
}