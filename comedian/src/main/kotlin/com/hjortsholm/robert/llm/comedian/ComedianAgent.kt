package com.hjortsholm.robert.llm.comedian

import com.hjortsholm.robert.llm.AiAgent
import com.hjortsholm.robert.llm.Skill
import com.hjortsholm.robert.llm.spec.v1.external.jokes.chucknorris.ChuckNorrisJokesApi
import org.springframework.ai.client.AiClient
import org.springframework.ai.ollama.client.OllamaClient
import org.springframework.ai.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.RestController



@RestController
class ComedianAgent(
    @Value("classpath:/prompts/system-definition.st")
    systemDefinitionResource: Resource,
    @Value("classpath:/prompts/function-call.st")
    functionCallResource: Resource,
    chuckNorrisJokes: ChuckNorrisJokesApi,
    mistral: AiClient,
) : AiAgent(
    name = "Comedian",
    skills = listOf(Skill(chuckNorrisJokes::getRandomJoke)),
    llm = mistral,
    systemDefinitionTemplate = PromptTemplate(systemDefinitionResource),
    functionCallTemplate = PromptTemplate(functionCallResource),
    null,
)

@Configuration
class LlmConfiguration {
    @Bean
    fun llama2(): AiClient = OllamaClient("http://127.0.0.1:11434", "llama2")

    @Bean
    fun mistral(): AiClient = OllamaClient("http://127.0.0.1:11434", "mistral")
}