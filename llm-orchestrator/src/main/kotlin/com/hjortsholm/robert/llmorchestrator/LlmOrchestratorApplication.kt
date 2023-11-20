package com.hjortsholm.robert.llmorchestrator

import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

//@EnableFeignClients(basePackages = ["com.hjortsholm.robert.spec.v1", "com.hjortsholm.robert.llm-orchestrator.v1"])
@SpringBootApplication
@EnableFeignClients
class LlmOrchestratorApplication

fun main(args: Array<String>) {
    runApplication<LlmOrchestratorApplication>(*args)
}

@Service
class OrchestratorService(private val mistral: AiClient) {

    /**
     * Ask the AI a question
     * 1. Ask a question
     * 2. Create a strategy for how to answer the question
     *      include parameters for each step
     *      include a way to use multiple parameters
     * 3. Execute the strategy
     *      3.1. Invoke the skill
     *      3.2. Evaluate the response
     *      3.3. Either improve or save result
     *      3.4. Invoke the next skill
     * 4. Evaluate result
     * 5. Formulate a response
     */
    fun ask(prompt: String): Any {
        val result = mistral.generate(prompt)

        return result
    }
}

@RestController
class LlmOrchestratorController {
}

@Configuration
class LlmConfiguration {
    @Bean
    fun llama2(): AiClient = OllamaClient("http://127.0.0.1:11434", "llama2")

    @Bean
    fun mistral(): AiClient = OllamaClient("http://127.0.0.1:11434", "mistral")

//	@Bean
//	fun systemPrompt(@Value("classpath:/prompts/system.st") template: Resource): PromptTemplate {
//		return PromptTemplate(template)
//	}
}