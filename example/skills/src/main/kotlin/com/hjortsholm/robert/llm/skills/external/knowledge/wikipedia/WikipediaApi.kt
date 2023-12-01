package com.hjortsholm.robert.llm.skills.external.knowledge.wikipedia

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "Wikipedia", url = "https://en.wikipedia.org/w/api.php")
interface WikipediaApi {
    @GetMapping("?action=query&prop=extracts&format=json&titles={titles}")
    @Operation(summary = "Query Wikipedia for more information about a subject")
    fun queryAboutSubject(@PathVariable("titles") subject: String): WikipediaQueryResult
}

