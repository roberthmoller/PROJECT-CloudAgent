package com.hjortsholm.robert.llm.v1

import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class LlmConfiguration {
    @Bean
    fun llama2(): AiClient = OllamaClient("http://127.0.0.1:11434", "llama2")

    @Bean
    fun mistral(): AiClient = OllamaClient("http://127.0.0.1:11434", "mistral")

    @Bean
    fun systemPrompt(@Value("classpath:/prompts/system.st") template: Resource): PromptTemplate {
        return PromptTemplate(template)
    }

    @Bean
    fun orchestratorPrompt(@Value("classpath:/prompts/orchestrator.st") template: Resource): PromptTemplate {
        return PromptTemplate(template)
    }
}