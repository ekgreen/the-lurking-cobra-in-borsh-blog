package com.lurking.cobra.blog.bot.impl.receive

import com.goodboy.telegram.bot.api.ParseMode
import com.goodboy.telegram.bot.api.methods.message.AnswerCallbackQuery
import com.goodboy.telegram.bot.api.methods.message.SendMessageApi
import com.goodboy.telegram.bot.api.methods.message.TelegramMessageApi
import com.goodboy.telegram.bot.spring.api.process.callback.CallbackEvent
import com.goodboy.telegram.bot.spring.api.process.callback.CallbackQueryListener
import com.lurking.cobra.blog.bot.api.publication.*
import com.lurking.cobra.blog.bot.api.flow.recommendation.Recommendation
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ItBlogRecommendationEventListener(private val messageApi: TelegramMessageApi, private val publisherService: PublisherService) : CallbackQueryListener {

    private val cache: MutableMap<Long, String> = ConcurrentHashMap()

    @Value("\${ItBlog.channel}")
    private lateinit var chatId: String

    companion object : KLogging() {
        const val RECOMMENDATION_CALLBACK_NAME: String = "recommendation"
        const val ACCEPT_DATA_NAME: String = "accept"
        const val DECLINE_DATA_NAME: String = "decline"
    }

    override fun callback(event: CallbackEvent): AnswerCallbackQuery {
        val recommendation: Recommendation = event.getData<Recommendation>()!!

        val messageId: Long = event.update.callbackQuery.message.id
        val decision = event.update.callbackQuery.data

        if(decision == ACCEPT_DATA_NAME && cache[messageId] == null)
            synchronized(this) {
                if(cache[messageId] == null){
                    messageApi.sendMessage(SendMessageApi()
                        .setChatId(chatId)
                        .setParseMode(ParseMode.HTML)
                        .setText(createRecommendationPost(recommendation)))
                }
                cache[messageId] = decision
            }
        return AnswerCallbackQuery()
    }

    override fun getEventName(): String = RECOMMENDATION_CALLBACK_NAME
}