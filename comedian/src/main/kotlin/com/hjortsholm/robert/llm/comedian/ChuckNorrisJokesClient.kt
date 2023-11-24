package com.hjortsholm.robert.llm.comedian

import com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris.ChuckNorrisJoke
import com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris.ChuckNorrisJokesApi
import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "ChuckNorrisJokes", url = "https://api.chucknorris.io/jokes/")
interface ChuckNorrisJokesClient: ChuckNorrisJokesApi