package com.hjortsholm.robert.llm.comedian

import com.hjortsholm.robert.llm.skills.external.jokes.chucknorris.ChuckNorrisJokesApi
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component

@FeignClient(name = "ChuckNorrisJokes", url = "https://api.chucknorris.io/jokes/")
interface ChuckNorrisJokesClient: ChuckNorrisJokesApi

@Component
class ChuckNorrisJokesWrapper(private val client: ChuckNorrisJokesClient) {
    fun getOneRandomJoke(): String = client.getOneRandomJoke().value
}