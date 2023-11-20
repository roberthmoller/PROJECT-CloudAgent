package com.hjortsholm.robert.spec.v1.advice

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@FeignClient(name = "Advice", url = "https://api.adviceslip.com")
interface AdviceApi {
    @GetMapping("/advice")
    @Operation(summary = "Get a random piece of advice")
    fun getRandomAdvice(): AdviceResult

    @GetMapping("/advice/search/{query}")
    @Operation(summary = "Search for a piece of advice about a given topic")
    fun searchForAdvice(@PathVariable("query") query: String): AdviceSearchResult
}