package com.hjortsholm.robert.llm.orchestrator

import com.hjortsholm.robert.llm.AiAgent
import com.hjortsholm.robert.llm.Skill
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.RestController

@RestController("/test")
class OrchestratorController(
    private val comedian: ComedianClient,
    mistral: AiClient,
    @Value("classpath:/prompts/system-definition.st") systemDefinitionResource: Resource,
    @Value("classpath:/prompts/function-call.st") functionCallResource: Resource,
) : AiAgent {
    override val name = "AI Orchestrator"
    override val purpose = "Call other AI agents to complete a goal"
    override val llm = mistral
    override fun skills() = listOf(comedian::challenge)
        .map { Skill(it) }
        .associateBy { it.skill }

    override val systemDefinitionTemplate = PromptTemplate(systemDefinitionResource)

    override val functionCallTemplate = PromptTemplate(functionCallResource)
}