package com.hjortsholm.robert.llm.v1

import com.google.gson.Gson
import com.hjortsholm.robert.llm.v1.skills.Skill
import com.hjortsholm.robert.llm.v1.skills.SkillInvocation
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class LlmService(
    private val mistral: AiClient,
    private val functionCallPrompt: PromptTemplate,
    private val responsePrompt: PromptTemplate,
    private val skills: Map<String, Skill>
) {
    fun ask(prompt: String): Any {
        val input = functionCallPrompt.render(
            mapOf(
                "skills" to skills.values.joinToString(",\n"),
                "prompt" to prompt,
            )
        )
        println("=========== Function Prompt ===========\n$input")
        val result = mistral.generate(input)
        println("=========== Invoke ===========\n$result")
        val gson = Gson()
        val invocation = gson.fromJson(result, SkillInvocation::class.java)
        val invocationResult = when (invocation.skill) {
            "answer" -> invocation.parameters["answer"] as String
            !in skills -> "No such skill: ${invocation.skill}"
            else -> skills[invocation.skill]!!.invoke(invocation.parameters)
        }
        println("=========== Result ===========\n$invocationResult")

        val responseInput = responsePrompt.render(
            mapOf(
                "prompt" to prompt,
                "context" to invocationResult,
            )
        )
        println("=========== Response Prompt ===========\n$responseInput")
        val responseResult = mistral.generate(responseInput)
        return responseResult
    }
}