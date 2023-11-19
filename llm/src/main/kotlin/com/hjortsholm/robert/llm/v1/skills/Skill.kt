package com.hjortsholm.robert.llm.v1.skills

import io.swagger.v3.oas.annotations.Operation
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotations

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