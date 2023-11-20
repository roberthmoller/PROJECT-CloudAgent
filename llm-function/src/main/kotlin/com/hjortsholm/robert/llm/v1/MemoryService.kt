package com.hjortsholm.robert.llm.v1

import org.springframework.ai.document.Document
import org.springframework.ai.embedding.Embedding
import org.springframework.ai.embedding.EmbeddingClient
import org.springframework.ai.embedding.EmbeddingResponse
import org.springframework.ai.reader.JsonReader
import org.springframework.ai.retriever.VectorStoreRetriever
import org.springframework.ai.vectorstore.InMemoryVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI


@Service
class MemoryService(
    private val mistralEmbedding: EmbeddingClient,
) {
    @Value("classpath:/static/bikes.min.json")
    private lateinit var bikesResource: Resource

    fun findBikesRelevantTo(query: String): List<Document> {
        // Step 1 - Load JSON document as Documents

        // Step 1 - Load JSON document as Documents
        println("Loading JSON as Documents")
        val jsonLoader = JsonReader(
            bikesResource,
            "name", "price", "shortDescription", "description"
        )
        val documents: List<Document> = jsonLoader.get()
        println("Loading JSON as Documents")

        // Step 2 - Create embeddings and save to vector store


        // Step 2 - Create embeddings and save to vector store
        println("Creating Embeddings...")
        val vectorStore: VectorStore = InMemoryVectorStore(mistralEmbedding)
        vectorStore.add(documents)
        println("Embeddings created.")

        // Step 3 retrieve related documents to query


        // Step 3 retrieve related documents to query
        val vectorStoreRetriever = VectorStoreRetriever(vectorStore)
        println("Retrieving relevant documents")
        val similarDocuments: List<Document> = vectorStoreRetriever.retrieve(query)
        println(String.format("Found %s relevant documents.", similarDocuments.size))
        return similarDocuments
    }
}

//@Profile("dev")
@FeignClient("Ollama", url = "http://localhost:11434")
interface OllamaApi {
    @PostMapping("/api/embeddings")
    fun embed(@RequestBody body: ModelAndPrompt): OllamaEmbeddingResponse
}

@Configuration
class OllamaEmbeddingClientConfiguration {
    @Bean
    fun llama2Embedding(ollamaApi: OllamaApi): OllamaEmbeddingClient = OllamaEmbeddingClient("llama2", ollamaApi)

    @Bean
    fun mistralEmbedding(ollamaApi: OllamaApi): OllamaEmbeddingClient = OllamaEmbeddingClient("mistral", ollamaApi)
}

data class OllamaEmbeddingResponse(
    val embeddings: List<Double>,
    val metadata: Map<String, String>
)

class OllamaEmbeddingClient(
    private val model: String,
    private val ollama: OllamaApi
) : EmbeddingClient {

    override fun embed(text: String): MutableList<Double> {
        return ollama.embed(ModelAndPrompt(model, text)).embeddings.toMutableList()
    }

    override fun embed(document: Document): MutableList<Double> {
        return embed(document.content)
    }

    override fun embed(texts: MutableList<String>): MutableList<MutableList<Double>> {
        return texts.map { embed(it) }.toMutableList()
    }

    override fun embedForResponse(texts: MutableList<String>): EmbeddingResponse {
        val embeddings = embed(texts).mapIndexed { index, it -> Embedding(it, index) }
        return EmbeddingResponse(embeddings, emptyMap())
    }
}

data class ModelAndPrompt(
    override val model: String,
    override val prompt: String
) : HasModel, HasPrompt

interface HasModel {
    val model: String
}

interface HasPrompt {
    val prompt: String
}