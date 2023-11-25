package com.hjortsholm.robert.llm.orchestrator

import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LlmConfiguration {
    @Bean
    fun mistral(): AiClient = clientOf("mistral")

    @Bean
    fun orca(): AiClient = clientOf("orca-mini")

    @Bean
    fun coder(): AiClient = clientOf("wizardcoder")

    private fun clientOf(model: String) = OllamaClient("http://127.0.0.1:11434", model)
}