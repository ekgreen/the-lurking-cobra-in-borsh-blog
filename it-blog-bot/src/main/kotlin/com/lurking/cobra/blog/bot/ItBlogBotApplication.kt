package com.lurking.cobra.blog.bot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ItBlogBotApplication{

    companion object {
        const val PROD_PROFILE: String = "prod"
        const val DEV_PROFILE : String = "dev"
    }
}

fun main(args: Array<String>) {
    runApplication<ItBlogBotApplication>(*args)
}
