package com.lurking.cobra.blog.generator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
class BlogGeneratorsApplication{

    companion object {
        const val PROD_PROFILE: String = "prod"
        const val DEV_PROFILE : String = "dev"
    }
}

fun main(args: Array<String>) {
    runApplication<BlogGeneratorsApplication>(*args)
}
