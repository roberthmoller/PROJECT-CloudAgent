package com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "ChuckNorrisJokes", url = "https://api.chucknorris.io/jokes/")
interface ChuckNorrisJokesApi {
    @GetMapping("/random")
    @Operation(summary = "Returns a random Chuck Norris joke")
    fun getRandomJoke(): ChuckNorrisJoke

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


