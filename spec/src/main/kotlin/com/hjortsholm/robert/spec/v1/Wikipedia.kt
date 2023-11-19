package com.hjortsholm.robert.spec.v1

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "Wikipedia", url = "https://en.wikipedia.org/w/api.php")
interface Wikipedia {
    @GetMapping(params = ["action=query", "format=json"])
    fun query(@RequestParam query: String)
}