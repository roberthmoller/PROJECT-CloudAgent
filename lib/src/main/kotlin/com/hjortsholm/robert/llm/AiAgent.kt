package com.hjortsholm.robert.llm

import com.google.gson.Gson
import com.hjortsholm.robert.llm.function.v1.skills.SkillInvocation
import jdk.jfr.Description
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.web.bind.annotation.*

interface AiApi {
    /**
     * Challenge the agent to complete a goal
     */
    @PostMapping
    fun challenge(goal: String): AiResponse

    /**
     * Return the skills the agent has
     */
    @RequestMapping(method = [RequestMethod.OPTIONS])
    fun skills(): Map<String, Skill>
}

interface AiAgent : AiApi {
    companion object {
        private const val MAX_ITERATIONS = 3
        private val GSON = Gson()
    }

    val name: String
    val purpose: String?
    val llm: AiClient
    val systemDefinitionTemplate: PromptTemplate
    val functionCallTemplate: PromptTemplate

    // todo: Cache llm responses
    override fun challenge(@RequestBody goal: String): AiResponse {
        val agentSkills = skills()
        val systemPrompt = systemDefinitionTemplate.render(
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
            if (hasFormulatedResponse) break
            val sessionPrompt = currentSession.joinToString("\n")
            val invocationAsString = llm.generate(sessionPrompt)
            val invocationAsJson = "{${invocationAsString.substringAfter('{').substringBeforeLast('}')}}"
            println("=========== Invoke ===========\n$invocationAsJson")
            val invocation = GSON.fromJson(invocationAsJson, SkillInvocation::class.java)
            val result = when (invocation.skill) {
                "answer" -> {
                    hasFormulatedResponse = true
                    invocation.parameters?.get("response")?.toString() ?: "No response"
                }
                in agentSkills -> agentSkills[invocation.skill]!!.invoke(invocation.parameters ?: emptyMap())
                else -> "No such skill ${invocation.skill}"
            }
            val resultAsJson = GSON.toJson(result)
            invocations.addLast(SkillResult(invocation, result))
            println("=========== Result ===========\n$resultAsJson")
            currentSession.addLast(
                functionCallTemplate.render(
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

data class AiResponse(
    val response: Any,
    val artefacts: List<SkillResult>,
)

data class SkillResult(
    val call: SkillInvocation,
    val result: Any
)