package com.hjortsholm.robert.llm.comedian

import com.hjortsholm.robert.llm.AiAgent
import com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris.ChuckNorrisJoke
import com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris.ChuckNorrisJokesApi
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KFunction

@RestController
class ComedianAgent(
    private val chuckNorrisJokes: ChuckNorrisJokesApi
) : AiAgent<String> {
    override val name: String = "Comedian"
    override val skills = listOf(chuckNorrisJokes::getRandomJoke)

    override fun ask(prompt: String): String {
        return "Comedian"
    }

    @GetMapping("/joke")
    fun joke(): String {
        return chuckNorrisJokes.getRandomJoke().value
    }
}