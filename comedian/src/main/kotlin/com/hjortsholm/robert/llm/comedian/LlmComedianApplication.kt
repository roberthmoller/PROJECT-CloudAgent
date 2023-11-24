package com.hjortsholm.robert.llm.comedian

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class LlmComedianApplication

fun main(args: Array<String>) {
    runApplication<LlmComedianApplication>(*args)
}

