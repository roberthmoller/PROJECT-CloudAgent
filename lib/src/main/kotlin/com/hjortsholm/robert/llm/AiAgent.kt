package com.hjortsholm.robert.llm

import com.google.gson.Gson
import com.hjortsholm.robert.llm.function.v1.skills.SkillInvocation
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.web.bind.annotation.*


abstract class AiAgent {
    val name: String
    val purpose: String?
    val skills: Map<String, Skill>
    val llm: AiClient
    val session: List<String>
    val systemDefinitionTemplate: PromptTemplate
    val functionCallTemplate: PromptTemplate

    constructor(
        name: String,
        skills: List<Skill>,
        llm: AiClient,
        systemDefinitionTemplate: PromptTemplate,
        functionCallTemplate: PromptTemplate,
        purpose: String? = null
    ) {
        this.name = name
        this.skills = skills.associateBy { it.skill }
        this.llm = llm
        this.session = mutableListOf()
        this.systemDefinitionTemplate = systemDefinitionTemplate
        this.functionCallTemplate = functionCallTemplate
        this.purpose = purpose
    }

    /**
     * Challenge the agent to complete a goal
     */
    @PostMapping
    fun challenge(@RequestBody goal: String): AiResponse {
        // todo: 2. Cache llm responses
        // todo: 1. Loop over result until we invoke "answer"
        val gson = Gson()
        val input = systemDefinitionTemplate.render(
            mapOf(
                "role" to name,
                "purpose" to (purpose ?: "address the user prompt to the best of your ability"),
                "skills" to skills.values.joinToString(",\n"),
                "prompt" to goal,
            )
        )
        val invocations = mutableListOf<SkillResult>()
        val currentSession = mutableListOf(input)
        var hasFormulatedResponse = false
        println("=========== Session Prompt ===========\n$goal")
        while (!hasFormulatedResponse) {
            val sessionPrompt = currentSession.joinToString("\n")
            val invocationAsString = llm.generate(sessionPrompt)
            val invocationAsJson = "{${invocationAsString.substringAfter('{').substringBeforeLast('}')}}"
            val invocation = gson.fromJson(invocationAsJson, SkillInvocation::class.java)
            println("=========== Invoke ===========\n$invocationAsJson")
            val result = when (invocation.skill) {
                "answer" -> {
                    hasFormulatedResponse = true
                    invocation.parameters?.get("response")?.toString() ?: "No response"
                }

                in skills -> skills[invocation.skill]!!.invoke(invocation.parameters ?: emptyMap())
                else -> "No such skill ${invocation.skill}"
            }
            val resultAsJson = gson.toJson(result)
            invocations.addLast(SkillResult(invocation , result))
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
        println("=========== Completed challenge ===========\n")
        println("Response: $response")
        println("Invocations: $invocations")
        println("=========== Completed summary ===========\n")
        println(currentSession.joinToString("\n"))

        return AiResponse(goal, response, invocations)
    }

    /**
     * Return the details of the current session
     */
    @GetMapping
    fun listSession(): List<String> {
        return session
    }

    /**
     * Return the skill the agent has
     */
    @RequestMapping(method = [RequestMethod.OPTIONS])
    fun listSkills(): List<Skill> {
        return skills.values.toList()
    }
}

data class AiResponse(
    val prompt: String,
    val response: Any,
    val artefacts: List<SkillResult>,
)

data class SkillResult(
    val call: SkillInvocation,
    val result: Any
)