package com.lurking.cobra.blog.farm.api.cycle.selection

import com.lurking.cobra.blog.farm.api.publication.model.Publication
import com.lurking.cobra.blog.farm.api.publication.model.PublicationStrategy

interface SelectionStrategy {

    /**
     * Процесс отбора публикаций заключается в отсеивании не подходящих публикаций,
     * например по тематике, определение потенциально "успешных" публикаций за которыми
     * требуется еще присмотреть и публикаций готовых к публикации
     *
     * @param publication - публикация
     * @return вердикт
     */
    fun selectPublicationStrategy(publication: Publication): PublicationStrategy
}