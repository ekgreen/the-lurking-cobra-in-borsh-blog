package com.lurking.cobra.blog.bot.impl

import com.goodboy.telegram.bot.api.ParseMode
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardButton
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardMarkup
import com.goodboy.telegram.bot.api.methods.Api
import com.goodboy.telegram.bot.api.methods.message.SendMessageApi
import com.goodboy.telegram.bot.spring.api.meta.Bot
import com.goodboy.telegram.bot.spring.api.meta.Hook
import com.goodboy.telegram.bot.spring.api.meta.messaging.Nickname
import com.goodboy.telegram.bot.spring.api.process.callback.BotCallbackApi
import com.goodboy.telegram.bot.spring.api.process.update.UpdateHolder
import com.goodboy.telegram.bot.spring.api.toolbox.Apis
import com.goodboy.telegram.bot.spring.impl.process.callback.CallbackAcceptorImpl
import com.lurking.cobra.blog.bot.api.publication.Publication
import com.lurking.cobra.blog.bot.api.publication.PublisherService
import com.lurking.cobra.blog.bot.api.publication.createReactionsMarkup
import com.lurking.cobra.blog.bot.impl.receive.ItBlogReactionEventListener
import com.lurking.cobra.blog.bot.impl.receive.ItBlogReactionEventListener.Companion.REACTION_CALLBACK_NAME
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled

@Bot("ItBlog")
class LurkingItBlogBot(private val callbackApi: BotCallbackApi, private val publisherService: PublisherService) {

    @Value("\${ItBlog.channel}")
    private lateinit var chatId: String

    @Hook(command = "/start")
    fun start(@Nickname username: String?): Api {
        val greeting = """
            Привет %s!
            
            Я IT блог бот, моя работа - постить в @LurkingItTestBlog, а также я умею выполнять ряд команда
            Введи /help чтобы узнать что я могу!
            """.trimIndent()
        return Apis.message(String.format(greeting, username ?: "незнакомец"))
    }

    @Hook(command = "/help")
    fun help(update: UpdateHolder): Api {
        return Apis.markdown(
            """
            Ты можешь управлять мной отправляя следующий набор команд:
            
            /search \- поиск статей
            /recommend \- порекомендовать ресурс: статья, книга, пост и так далее
            """.trimIndent()
        )
    }

    @Hook(command = "/test")
    fun test(update: UpdateHolder) {
        callbackApi.sendMessageWithCallbackQuery(CallbackAcceptorImpl<Any>(REACTION_CALLBACK_NAME, SendMessageApi()
                    .setChatId(chatId)
                    .setParseMode(ParseMode.HTML)
                    .setText("""
                        <u><b>Дорожная карта по изучению C++</b></u>
                        <pre>#C++ #GitHub #Карьера в IT-индустрии #Учебный процесс в IT</pre>
                        
                        <i>publication</i>: https://habr.com/ru/post/593503/
                        """.trimIndent())
                    .setReplyMarkup(createReactionsMarkup())
            ).setData(Publication("1", "title", "habr"))
        )
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun publishInChannel() {
        val post: Publication? = publisherService.publicationForPost()

        if(post != null) {
            callbackApi.sendMessageWithCallbackQuery(CallbackAcceptorImpl<Any>(
                    REACTION_CALLBACK_NAME, SendMessageApi()
                        .setChatId(chatId)
                        .setParseMode(ParseMode.HTML)
                        .setText(
                            """
                        <u><b>${post.title}</b></u>
                        <pre>${post.tags.joinToString(separator = " ") { "#$it" }}</pre>

                        <i>publication</i>: ${post.uri}
                        """.trimIndent()
                        )
                        .setReplyMarkup(createReactionsMarkup())
                )
                    .setData(post)
            )

            publisherService.publicationPosted(post)
        }
    }
//
//    @Scheduled(cron = "0 */9 * * * *")
//    fun createWeeklyDigest() {
//        logger.info { "publication in createWeeklyDigest" }
//    }

    companion object : KLogging()
}