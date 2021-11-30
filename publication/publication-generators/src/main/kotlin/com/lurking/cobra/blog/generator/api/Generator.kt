package com.lurking.cobra.blog.generator.api

import com.lurking.cobra.blog.generator.api.publication.PublicationProducer

interface Generator {

    /**
     * Генератор возвращает итератор с поставщиками публикаций
     *
     * Поставщики могут реализовывать ленивую вычитку публикаций исходя
     * из требуемых условий API
     *
     * @see PublicationProducer
     */
    fun getPublications(): Iterator<PublicationProducer>
}