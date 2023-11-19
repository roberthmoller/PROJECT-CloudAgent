package com.hjortsholm.robert.llm.v1

import com.hjortsholm.robert.llm.v1.skills.Skill
import com.hjortsholm.robert.spec.v1.advice.AdviceApi
import com.hjortsholm.robert.spec.v1.images.LoremPicsumApi
import com.hjortsholm.robert.spec.v1.images.UnsplashApi
import com.hjortsholm.robert.spec.v1.jokes.chucknorris.ChuckNorrisJokesApi
import com.hjortsholm.robert.spec.v1.knowledge.wikipedia.WikipediaApi
import com.netflix.discovery.EurekaClient
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LlmController(
    private val llm: LlmService,
    private val eureka: EurekaClient,
    private val skills: Map<String, Skill>,
    private val chuckNorrisSays: ChuckNorrisJokesApi,
    private val wikipedia: WikipediaApi,
    private val advice: AdviceApi,
    private val picsum: LoremPicsumApi,
    private val unsplash: UnsplashApi
) {

    @PostMapping("/ai")
    @Operation(summary = "Ask the AI a question")
    @ApiResponse(description = "The AI's answer")
    fun index(@RequestBody prompt: String): Any {
        return llm.ask(prompt)
    }

    @PostMapping("/wiki")
    fun wiki(@RequestBody subject: String): String {
        return wikipedia.queryAboutSubject(subject).query.pages.values.first().extract
    }

    @PostMapping("/advice")
    fun advice(@RequestBody subject: String): String {
        return advice.searchForAdvice(subject).slips.first().advice
    }

    @GetMapping("/picsum", produces = ["image/jpeg"])
    fun picsum(): ByteArray {
        return picsum.getRandomPhoto(500, 500)
    }
    @GetMapping("/unsplash", produces = ["image/jpeg"])
    fun unsplash(): ByteArray {
        return unsplash.getPhotoOfSubject("nature,winter")
    }

    @GetMapping("/skills")
    fun skills(): List<String> {
        return skills.values.map { it.toString() }
    }

    @PostMapping("/chuck")
    @Operation(
        summary = "Chuck Norris says something", responses = [
            ApiResponse(
                description = "A random Chuck Norris joke", content = [
                    Content(mediaType = "text/plain", schema = Schema(implementation = String::class))
                ]
            )
        ]
    )
    fun chuck(): String {
        return chuckNorrisSays.getRandomJoke().value
    }
}

