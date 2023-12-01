package com.hjortsholm.robert.llm.orchestrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(basePackages = ["com.hjortsholm.robert.llm"])
@SpringBootApplication
class OrchestratorApplication

fun main(args: Array<String>) {
    runApplication<OrchestratorApplication>(*args)
}
