package com.hjortsholm.robert.llm.comedian

import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LlmConfiguration {
    @Bean
    fun mistral(): AiClient = OllamaClient("http://127.0.0.1:11434", "mistral")
}