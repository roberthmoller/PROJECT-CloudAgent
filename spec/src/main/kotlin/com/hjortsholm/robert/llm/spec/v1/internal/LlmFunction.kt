package com.hjortsholm.robert.llm.spec.v1.internal

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


interface LlmFunction<T> {
    @PostMapping("/call")
    @Operation(
        summary = "Ask the AI a question", responses = [
            ApiResponse(responseCode = "200", description = "The AI's answer")
        ]
    )
    fun call(@RequestBody prompt: String): T
}