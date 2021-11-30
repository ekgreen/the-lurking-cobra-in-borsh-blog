package com.lurking.cobra.blog.generator.api.publication

interface PublicationProducer {

    /**
     * Возвращает данные о публикации по запросу
     *
     * @param publication callback - возвращает true, если публикация успешно обработана
     *
     * @see Publication
     */
    fun produce(publication: (Publication) -> Boolean)
}