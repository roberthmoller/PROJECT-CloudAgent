package com.hjortsholm.robert.llmorchestrator

import com.hjortsholm.robert.spec.v1.internal.LlmFunction
import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableFeignClients
class LlmOrchestratorApplication

fun main(args: Array<String>) {
    runApplication<LlmOrchestratorApplication>(*args)
}

@FeignClient(name = "LlmFunction", url = "http://localhost:9090")
interface ExampleLlmFunction : LlmFunction<Any>

@Service
class OrchestratorService(
    private val mistral: AiClient,
    private val orchestratorPrompt: PromptTemplate,
    private val llmFunction: ExampleLlmFunction
) {

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
//    fun ask(prompt: String): Any {
//        val renderedPrompt = orchestratorPrompt.render(
//            mapOf(
//                "prompt" to prompt,
//            )
//        )
//
//        val strategy = mistral.generate(renderedPrompt)
//        println("=========== Strategy ===========\n$strategy")
//        llmFunction.call(strategy)
//        return result
//    }
}


@RestController
class LlmOrchestratorController(
    private val orchestrator: OrchestratorService
) : LlmFunction<Any> {
    override fun call(prompt: String): Any = orchestrator.ask(prompt)
}

@Configuration
class LlmConfiguration {
    @Bean
    fun llama2(): AiClient = OllamaClient("http://127.0.0.1:11434", "llama2")

    @Bean
    fun mistral(): AiClient = OllamaClient("http://127.0.0.1:11434", "mistral")

    @Bean
    fun orchestratorPrompt(@Value("classpath:/templates/orchestrator.st") template: Resource): PromptTemplate {
        return PromptTemplate(template)
    }
}

data class MicroService(
    val function: LlmFunction<Any>,
) {

}
