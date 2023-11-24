package com.hjortsholm.robert.llm.spec.v1

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Description
import org.springframework.web.bind.annotation.GetMapping
import java.util.*


@FeignClient("CloudClock")
interface CloudClock {
    @GetMapping("/current-date")
    @Description("Returns the current date")
    fun currentDate(): Date
}

