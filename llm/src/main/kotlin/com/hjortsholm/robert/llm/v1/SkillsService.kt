package com.hjortsholm.robert.llm.v1

import com.hjortsholm.robert.spec.v1.ChuckNorrisJokes
import io.swagger.v3.oas.annotations.Operation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotations

@Configuration
class SkillConfiguration {
    @Bean
    fun all(
        chuckNorrisJokes: ChuckNorrisJokes
    ): Map<String, Skill> {
        return listOf(
            Skill(chuckNorrisJokes::randomJoke),
            Skill(chuckNorrisJokes::listPossibleJokeCategories),
            Skill(chuckNorrisJokes::searchForJokes)
        ).associateBy { it.skill }
    }
}

data class Skill(val function: KFunction<Any>) {
    val skill get() = function.name
    val description get() = function.findAnnotations<Operation>().firstOrNull()?.summary
    val params: Map<String, String>
        get() = function.parameters
            .associate {
                it.name!! to it.type.toString().substringAfterLast('.')
            }

    fun invoke(args: Map<String, Any?>): Any {
        val functionArgs = args.mapKeys { (key, value) ->
            function.parameters.find { it.name == key }!!
        }
        return function.callBy(functionArgs)
    }

    override fun toString(): String {
        return "{\"skill\": \"$skill\", \"description\":\"$description\", \"params\": $params}"
    }
}
