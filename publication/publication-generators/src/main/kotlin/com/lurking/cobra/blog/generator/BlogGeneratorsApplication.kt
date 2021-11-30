package com.lurking.cobra.blog.generator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogGeneratorsApplication

fun main(args: Array<String>) {
    runApplication<BlogGeneratorsApplication>(*args)
}
