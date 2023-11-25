package com.hjortsholm.robert.llm

import io.swagger.v3.oas.annotations.Operation
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotations


data class Skill(
    val skill: String,
    val parameters: Map<String, String>,
    val invoke: (Map<String, Any?>) -> Any,
) {
    constructor(function: KFunction<Any>, skill: String? = null) : this(
        skill = skill ?: function.name,
        parameters = function.parameters.associate { (it.name ?: "") to it.type.toString().substringAfterLast('.') },
        invoke = { args ->
            val functionArgs = function.parameters
                .associateWith { args[it.name] }
            if (functionArgs.any { it.value == null }) {
                throw IllegalArgumentException("Missing arguments: ${functionArgs.filter { it.value == null }.keys}")
            }
            if (args.isEmpty()) function.call()
            else function.callBy(functionArgs)
        }
    )


    override fun toString(): String {
        //language=JSON
        return "{\"skill\":\"$skill\",\"parameters\":$parameters}"
    }
}