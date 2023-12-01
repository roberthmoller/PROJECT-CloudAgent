package com.hjortsholm.robert.llm.skills.external.advice

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "Advice", url = "https://api.adviceslip.com")
interface AdviceApi {
    @GetMapping("/advice")
    @Operation(summary = "Get a random piece of advice")
    fun getRandomAdvice(): AdviceResult

    @GetMapping("/advice/search/{query}")
    @Operation(summary = "Search for a piece of advice about a given topic")
    fun searchForAdvice(@PathVariable("query") query: String): AdviceSearchResult
}