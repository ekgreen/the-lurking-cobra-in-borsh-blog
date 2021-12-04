package com.lurking.cobra.blog.farm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PublicationStatisticApplication{

    companion object {
        const val PROD_PROFILE: String = "prod"
        const val DEV_PROFILE : String = "dev"
    }

}

fun main(args: Array<String>) {
    runApplication<PublicationStatisticApplication>(*args)
}
