package com.lurking.cobra.blog.bot.impl.receive

import com.goodboy.telegram.bot.api.methods.message.AnswerCallbackQuery
import com.goodboy.telegram.bot.api.methods.message.TelegramMessageApi
import com.goodboy.telegram.bot.api.methods.message.edit.EditMessageReplyMarkupApi
import com.goodboy.telegram.bot.spring.api.process.callback.CallbackEvent
import com.goodboy.telegram.bot.spring.api.process.callback.CallbackQueryListener
import com.lurking.cobra.blog.bot.api.publication.*
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ItBlogReactionEventListener(private val messageApi: TelegramMessageApi, private val publisherService: PublisherService) : CallbackQueryListener {

    private val cache: MutableMap<Long, PublicationReactions> = ConcurrentHashMap()

    @Value("\${ItBlog.channel}")
    private lateinit var chatId: String

    companion object : KLogging() {
        const val REACTION_CALLBACK_NAME: String = "reaction"
        const val LIKE_EVENT_NAME: String = "like"
        const val ZIPPER_EVENT_NAME: String = "zipper"
        const val POKER_FACE_EVENT_NAME: String = "poker_face"
    }

    override fun callback(event: CallbackEvent): AnswerCallbackQuery {
        val post: Publication = event.getData<Publication>()!!

        val messageId: Long = event.update.callbackQuery.message.id
        val reaction = event.update.callbackQuery.data

        val reactions: PublicationReactions =  cache[messageId] ?: post.reactions?: PublicationReactions()

        when (reaction) {
            ZIPPER_EVENT_NAME -> reactions.lightingCount += 1
            LIKE_EVENT_NAME -> reactions.likesCount += 1
            POKER_FACE_EVENT_NAME -> reactions.pokerFaceCount += 1
        }

        messageApi.editMessageReplyMarkup(EditMessageReplyMarkupApi()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setReplyMarkup(createReactionsMarkup(reactions))
        )

        cache[messageId] = reactions

        publisherService.publicationReaction(ReactionEvent(post.id, reaction, 1))
        return AnswerCallbackQuery()
    }

    override fun getEventName(): String = REACTION_CALLBACK_NAME

}