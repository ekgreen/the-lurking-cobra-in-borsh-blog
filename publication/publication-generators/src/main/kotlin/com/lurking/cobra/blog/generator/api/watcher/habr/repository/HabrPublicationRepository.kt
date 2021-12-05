package com.lurking.cobra.blog.generator.api.watcher.habr.repository

import com.lurking.cobra.blog.generator.api.watcher.habr.HubType
import com.lurking.cobra.blog.generator.api.publication.Publication
import java.time.LocalDateTime

interface HabrPublicationRepository {

    /**
     * Получение индекса последней вычитанной статьи
     * Статья считается последней та у которой максимальный индекс
     *
     * @param subscription название подписки
     */
    fun getLastPublicationTimestampBySubscriptionNameAndType(subscriptionName: String, subscriptionType: HubType) : LocalDateTime?

    /**
     * Сохранить публикацию в логе, только закомиченные публикацию считаются исполненными
     *
     * @param publication публикация
     * @see HabrPublicationRepository#commit()
     */
    fun save(publication: Publication)

    /**
     * Отметить в логе публикацию, как исполненную
     *
     * @param publication публикация
     */
    fun commit(publication: Publication)
}