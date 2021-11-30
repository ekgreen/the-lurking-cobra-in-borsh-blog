package com.lurking.cobra.blog.bot

import com.goodboy.telegram.bot.api.methods.Api
import com.goodboy.telegram.bot.spring.api.meta.Bot
import com.goodboy.telegram.bot.spring.api.meta.Hook
import com.goodboy.telegram.bot.spring.api.meta.messaging.Nickname
import com.goodboy.telegram.bot.spring.api.toolbox.Apis

@Bot("ItBlog")
class LurkingItBlogBot {

    @Hook(command = "/start")
    fun start(@Nickname username: String?): Api {
        val greeting = """Привет %s!
            
Я IT блог бот, моя работа - постить в @LurkingItTestBlog, а также я умею выполнять ряд команда

Введи /help чтобы узнать что я могу!"""
        return Apis.message(String.format(greeting, username ?: "незнакомец"))
    }

    @Hook
    fun help(): Api {
        return Apis.markdown(
            """Ты можешь управлять мной отправляя следующий набор команд:
                
/search \- поиск статей
/recommend \- порекомендовать ресурс: статья, книга, пост и так далее
"""
        )
    }
}