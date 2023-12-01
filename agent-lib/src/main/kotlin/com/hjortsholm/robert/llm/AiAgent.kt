package com.hjortsholm.robert.llm

import com.google.gson.Gson
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.web.bind.annotation.*

abstract class AiAgent : AiApi {
    companion object {
        private const val MAX_ITERATIONS = 3
        private val GSON = Gson()
    }

    abstract val name: String
    abstract val purpose: String?
    abstract val llm: AiClient
    abstract val systemDefinitionTemplate: PromptTemplate
    abstract val functionCallTemplate: PromptTemplate

    override fun challenge(goal: String): AiResponse {
        val agentSkills = skills()
        val systemPrompt = PromptTemplate(systemDefinitionTemplate.template).render(
            mapOf(
                "role" to name,
                "purpose" to (purpose ?: "address the user prompt to the best of your ability"),
                "skills" to agentSkills.values.joinToString(",\n"),
                "goal" to goal,
            )
        )
        val invocations = mutableListOf<SkillResult>()
        val currentSession = mutableListOf(systemPrompt)
        var hasFormulatedResponse = false
        println("=========== System Message ===========\n$systemPrompt")
        println("=========== Session Prompt ===========\n$goal")
        for (i in 0 until MAX_ITERATIONS) {
            val sessionPrompt = currentSession.joinToString("\n")
            val rawInvocationAsString = llm.generate(sessionPrompt)
            val rawInvocationAsJson = "[{${rawInvocationAsString.substringAfter('{').substringBeforeLast('}')}}]"
            val invocation = GSON.fromJson(rawInvocationAsJson, Array<SkillInvocation>::class.java).first()
            val invocationAsJson = GSON.toJson(invocation)
            println("=========== Invoke ===========\n$invocationAsJson")
            val result = when (invocation.skill) {
                "answer" -> {
                    hasFormulatedResponse = true
                    invocation.parameters?.get("response")?.toString() ?: "No response"
                }

                in agentSkills -> agentSkills[invocation.skill]!!.invoke(invocation.parameters ?: emptyMap())
                else -> "No such skill ${invocation.skill}"
            }
            if (hasFormulatedResponse) break
            val resultAsJson = GSON.toJson(result)
            println("=========== Result ===========\n$resultAsJson")
            invocations.addLast(SkillResult(invocation, result))
            currentSession.addLast(
                PromptTemplate(functionCallTemplate.template).render(
                    mapOf(
                        "function" to invocationAsJson,
                        "result" to resultAsJson,
                    )
                )
            )
        }
        val response = invocations.last().result
        println("=========== Details ===========\n")
        println("Response: ${GSON.toJson(response)}")
        println("Invocations: ${GSON.toJson(invocations)}")
        println("=========== Summary ===========\n")
        println(currentSession.joinToString("\n"))

        return AiResponse(response, invocations)
    }
}