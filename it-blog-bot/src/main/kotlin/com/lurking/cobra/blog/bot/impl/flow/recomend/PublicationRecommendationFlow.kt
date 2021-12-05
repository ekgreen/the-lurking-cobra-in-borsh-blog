package com.lurking.cobra.blog.bot.impl.flow.recomend

import com.goodboy.telegram.bot.api.ParseMode
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardButton
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardMarkup
import com.goodboy.telegram.bot.api.methods.Api
import com.goodboy.telegram.bot.api.methods.message.SendMessageApi
import com.goodboy.telegram.bot.spring.api.meta.Flow
import com.goodboy.telegram.bot.spring.api.meta.Hook
import com.goodboy.telegram.bot.spring.api.meta.flow.Entity
import com.goodboy.telegram.bot.spring.api.meta.messaging.MessageText
import com.goodboy.telegram.bot.spring.api.meta.messaging.Nickname
import com.goodboy.telegram.bot.spring.api.process.behaviour.flow.FlowApi
import com.goodboy.telegram.bot.spring.api.process.behaviour.gateway.transition.Transition
import com.goodboy.telegram.bot.spring.api.process.callback.BotCallbackApi
import com.goodboy.telegram.bot.spring.api.toolbox.Apis
import com.goodboy.telegram.bot.spring.impl.process.callback.CallbackAcceptorImpl
import com.lurking.cobra.blog.bot.api.flow.recommendation.PublicationSource
import com.lurking.cobra.blog.bot.api.flow.recommendation.Recommendation
import com.lurking.cobra.blog.bot.api.flow.recommendation.findSourceByText
import com.lurking.cobra.blog.bot.api.publication.PublisherService
import com.lurking.cobra.blog.bot.api.publication.createRecommendationPost
import com.lurking.cobra.blog.bot.impl.receive.ItBlogRecommendationEventListener.Companion.ACCEPT_DATA_NAME
import com.lurking.cobra.blog.bot.impl.receive.ItBlogRecommendationEventListener.Companion.DECLINE_DATA_NAME
import com.lurking.cobra.blog.bot.impl.receive.ItBlogRecommendationEventListener.Companion.RECOMMENDATION_CALLBACK_NAME
import org.springframework.beans.factory.annotation.Value

@Flow("PublicationRecommendation")
class PublicationRecommendationFlow(private val publisherService: PublisherService, private val callbackApi: BotCallbackApi) {

    @Value("\${ItBlog.editor}")
    private lateinit var chatId: String

    @Hook
    fun start(@Entity recommendation: Recommendation, @MessageText text: String, @Nickname nickname: String): FlowApi<Api> {
        return FlowApi.api(
            {
                Apis.keyboard(
                    "Для того чтобы порекомендовать статью, видео или прочей ресурс для публикации потребуется ответить на пару моих вопросов",
                    2,
                    "Хабр", "Другой"
                )
            },
            {
                val source: PublicationSource = findSourceByText(text).also { recommendation.source = it }

                Transition.simple("Enter")
            }
        ) { Transition.self("Указан несуществующий тип источника, попробуйте еще раз...") }
    }

    @Hook
    fun enter(@Entity recommendation: Recommendation, @MessageText text: String, @Nickname nickname: String): FlowApi<Api> {
        return FlowApi.api(
            { Apis.message("Введите ссылку на источник в формате: ${recommendation.source!!.patter}") },
            {
                if(!recommendation.source!!.patter.matcher(text).matches())
                    throw IllegalStateException("не допустимый формат входных данных")

                recommendation.link = text

                if(recommendation.source == PublicationSource.UNKNOWN) {
                    Transition.simple("Title")
                } else {
                    publisherService.publishRecommendation(recommendation)
                    Transition.end("Спасибо, ваша рекомендация отправлена на рассмотрение")
                }

            }
        ) { Transition.self("Указан несуществующий тип источника, попробуйте еще раз...") }
    }

    @Hook
    fun title(@Entity recommendation: Recommendation, @MessageText text: String, @Nickname nickname: String): FlowApi<Api> {
        return FlowApi.api(
            { Apis.message("Введите заголовок и описание ресурса, чтобы редактору было проще понять о чем идет речь\n\nЗаголовок:") },
            {
                recommendation.title = text
                Transition.simple("Description")
            }
        )
    }

    @Hook
    fun description(@Entity recommendation: Recommendation, @MessageText text: String, @Nickname nickname: String): FlowApi<Api> {
        return FlowApi.api(
            { Apis.message("Описание:") },
            {
                recommendation.description = text
                callbackApi.sendMessageWithCallbackQuery(CallbackAcceptorImpl<Recommendation>(RECOMMENDATION_CALLBACK_NAME, SendMessageApi()
                    .setChatId(chatId)
                    .setParseMode(ParseMode.HTML)
                    .setText(createRecommendationPost(recommendation))
                    .setReplyMarkup(InlineKeyboardMarkup()
                            .setKeyboardLine(
                                InlineKeyboardButton()
                                    .setText("Опубликовать")
                                    .setCallbackData(ACCEPT_DATA_NAME),
                                InlineKeyboardButton()
                                    .setText("Отказать")
                                    .setCallbackData(DECLINE_DATA_NAME))
                )).setData(recommendation))
                Transition.end("Спасибо, ваша рекомендация отправлена на рассмотрение")
            }
        )
    }
}