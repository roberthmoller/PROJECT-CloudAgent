package com.hjortsholm.robert.llm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.hjortsholm.robert.spec.v1", "com.hjortsholm.robert.llm"])
class LlmApplication

fun main(args: Array<String>) {
    runApplication<LlmApplication>(*args)
}

