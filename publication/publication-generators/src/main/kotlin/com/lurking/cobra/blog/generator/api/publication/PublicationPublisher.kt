package com.lurking.cobra.blog.generator.api.publication

interface PublicationPublisher {

    /**
     * Опубликовать статью во внешний или внутренний сервис
     *
     * @param publication публикация
     */
    fun publish(publication: Publication)
}