package com.hjortsholm.robert.llm.orchestrator

import com.hjortsholm.robert.llm.AiApi
import com.hjortsholm.robert.llm.AiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Description
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("Comedian")
interface ComedianClient : AiApi {
    @GetMapping
    @Description("Tell jokes")
    override fun challenge(goal: String): AiResponse
}