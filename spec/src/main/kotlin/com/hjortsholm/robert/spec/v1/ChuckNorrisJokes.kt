package com.hjortsholm.robert.spec.v1

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Collections.emptyList

@FeignClient(name = "ChuckNorrisJokes", url = "https://api.chucknorris.io/jokes/")
interface ChuckNorrisJokes {
    @GetMapping("/random")
    @Operation(summary = "Returns a random Chuck Norris joke")
    fun randomJoke(): ChuckNorrisJoke

    @GetMapping("/random")
    @Operation(summary = "Returns a random Chuck Norris joke from a given category")
    fun randomJokeInCategory(@RequestParam("category") category: String): ChuckNorrisJoke

    @GetMapping("/categories")
    @Operation(summary = "Returns a list of all possible categories")
    fun listPossibleJokeCategories(): List<String>

    @GetMapping("/search")
    @Operation(summary = "Search for Chuck Norris jokes")
    fun searchForJokes(@RequestParam("search") search: String): ChuckNorrisJokeSearchResult
}


data class ChuckNorrisJoke @JsonCreator constructor(
    @JsonProperty("categories") val categories: List<String>? = emptyList(),
    @JsonProperty("icon_url") val iconUrl: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("updated_at") val updatedAt: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("value") val value: String,
)

data class ChuckNorrisJokeSearchResult @JsonCreator constructor(
    @JsonProperty("total") val total: Int,
    @JsonProperty("result") val result: List<ChuckNorrisJoke>,
)