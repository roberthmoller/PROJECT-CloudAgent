package com.hjortsholm.robert.llm.orchestrator

import com.deepl.api.Translator
import com.hjortsholm.robert.llm.AiAgent
import com.hjortsholm.robert.llm.Skill
import org.springframework.ai.client.AiClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController

@RestController("/test")
class OrchestratorController(
    private val comedian: ComedianClient,
    private val deepl: DeeplClient,
    mistral: AiClient,
    @Value("classpath:/prompts/system-definition.st") systemDefinitionResource: Resource,
    @Value("classpath:/prompts/function-call.st") functionCallResource: Resource,
) : AiAgent {
    override val name = "AI Orchestrator"
    override val purpose = "Call other AI agents to complete a goal"
    override val llm = mistral
    override fun skills() = listOf(Skill(comedian::challenge, "comedian"), Skill(deepl::translate))
        .associateBy { it.skill }

    override val systemDefinitionTemplate = PromptTemplate(systemDefinitionResource)

    override val functionCallTemplate = PromptTemplate(functionCallResource)
}

@Configuration
class DeeplConfiguration {
    @Bean
    fun translator(@Value("\${deepl.authKey}") deeplAuthKey: String): Translator = Translator(deeplAuthKey)
}

@Component
class DeeplClient(private val translator: Translator) {
    fun translate(text: String, twoDigitTargetLanguageCode: String): String {
        return translator.translateText(text, null, twoDigitTargetLanguageCode).text
    }
}
