package com.hjortsholm.robert.llm.v1

import com.google.gson.Gson
import com.hjortsholm.robert.spec.v1.ChuckNorrisJokes
import com.netflix.discovery.EurekaClient
import com.netflix.discovery.shared.Application
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LlmController(
    private val llm: LlmService,
    private val eureka: EurekaClient,
    private val chuckNorrisSays: ChuckNorrisJokes
) {

    @PostMapping("/ai")
    @Operation(summary = "Ask the AI a question")
    @ApiResponse(description = "The AI's answer")
    fun index(@RequestBody prompt: String): String {
        return llm.ask(prompt)
    }

    @PostMapping("/foo")
    @Operation(summary = "List all registered applications")
    @ApiResponse(description = "List of applications")
    fun foo(): List<Application> {

        return eureka.applications.registeredApplications ?: listOf()
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
        return chuckNorrisSays.randomJoke().value
    }
}

@Service
class LlmService(
    private val mistral: AiClient,
    private val systemPrompt: PromptTemplate,
    private val orchestratorPrompt: PromptTemplate,
    private val skills: Map<String, Skill>
) {
    fun ask(prompt: String): String {
        val input = orchestratorPrompt.render(
            mapOf(
                "skills" to skills.values.joinToString(",\n"),
                "prompt" to prompt,
            )
        )
        println("=========== Prompt ===========\n$input")
        val result = mistral.generate(input)
        println("=========== Invoke ===========\n$result")
        val gson = Gson()
        val invocation = gson.fromJson(result, SkillInvocation::class.java)
        val invocationResult = when (invocation.skill) {
            "answer" -> invocation.parameters["answer"] as String
            !in skills -> "No such skill: ${invocation.skill}"
            else -> gson.toJson(skills[invocation.skill]!!.invoke(invocation.parameters))
        }
        println("=========== Result ===========\n$invocationResult")
        return invocationResult
    }
}

data class SkillInvocation(
    val skill: String,
    val parameters: Map<String, Any?>
)