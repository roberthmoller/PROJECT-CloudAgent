package com.hjortsholm.robert.llm.discovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class DiscoveryApplication

fun main(args: Array<String>) {
    runApplication<DiscoveryApplication>(*args)
}
