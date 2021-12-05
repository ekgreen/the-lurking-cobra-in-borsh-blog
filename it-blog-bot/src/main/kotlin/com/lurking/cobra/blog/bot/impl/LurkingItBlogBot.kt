package com.lurking.cobra.blog.bot.impl

import com.goodboy.telegram.bot.api.ParseMode
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardButton
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardMarkup
import com.goodboy.telegram.bot.api.methods.Api
import com.goodboy.telegram.bot.api.methods.message.SendMessageApi
import com.goodboy.telegram.bot.api.methods.message.TelegramMessageApi
import com.goodboy.telegram.bot.spring.api.meta.Bot
import com.goodboy.telegram.bot.spring.api.meta.Hook
import com.goodboy.telegram.bot.spring.api.meta.messaging.MessageText
import com.goodboy.telegram.bot.spring.api.meta.messaging.Nickname
import com.goodboy.telegram.bot.spring.api.process.callback.BotCallbackApi
import com.goodboy.telegram.bot.spring.api.process.update.UpdateHolder
import com.goodboy.telegram.bot.spring.api.toolbox.Apis
import com.goodboy.telegram.bot.spring.impl.process.callback.CallbackAcceptorImpl
import com.lurking.cobra.blog.bot.api.publication.*
import com.lurking.cobra.blog.bot.impl.receive.ItBlogReactionEventListener
import com.lurking.cobra.blog.bot.impl.receive.ItBlogReactionEventListener.Companion.REACTION_CALLBACK_NAME
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.Scheduled
import java.security.PrivateKey

@Bot("ItBlog")
class LurkingItBlogBot(private val callbackApi: BotCallbackApi,
                       private val publisherService: PublisherService,
                       private val publicationApi: PublicationApi,
                       private val messageApi: TelegramMessageApi) {

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

    @Hook(command = "/search")
    fun search(@MessageText text: String): Api {
        val tags: Set<String> = text.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toSet()

        if(tags.isEmpty()){
            return Apis.markdown("""
                Вы не ввели теги или ввели их **в не правильном формате**, пожалуйста напишите каждый тег через запятую\.
                Например: __java__, __kotlin__
            """.trimIndent())
        } else {
            val found: List<Publication> = publicationApi.findPostByTags(tags, 10)

            if(found.isEmpty())
                return Apis.markdown("""
                По вашему запросу не найдено **ни одной** публикации\. Попробуйте изменить запрос или подождать пока появятся новые статьи
                Напоминаю, поиск производится по точному соответствию всех тегов, то есть **все** указанные вами теги должны присутствовать в публикации
            """.trimIndent())
            else
                return SendMessageApi()
                    .setChatId(chatId)
                    .setParseMode(ParseMode.HTML)
                    .setText("<b>Результат поиска:</b>\n\n ${found.joinToString(separator = "\n\n") { createPublicationPost(it) }}")
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun publishInChannel() {
        publisherService.publicationForPost()?.also { post ->
            callbackApi.sendMessageWithCallbackQuery(
                CallbackAcceptorImpl<Publication>(REACTION_CALLBACK_NAME, SendMessageApi()
                    .setChatId(chatId)
                    .setParseMode(ParseMode.HTML)
                    .setText(createPublicationPost(post))
                    .setReplyMarkup(createReactionsMarkup()))
                .setData(post)
            )

            publisherService.publicationPosted(post)
        }
    }

    @Scheduled(cron = "0 */1 * * * *")
    fun createWeeklyDigest() {
        val digest: List<Publication> = publicationApi.getDigest(10)

        if(digest.isNotEmpty()){
            messageApi.sendMessage(SendMessageApi()
                .setChatId(chatId)
                .setParseMode(ParseMode.HTML)
                .setText("<b>Недельный дайджест:</b>\n\n ${digest.joinToString(separator = "\n\n") { createPublicationPost(it) }}"))
        }
    }

    companion object : KLogging()
}