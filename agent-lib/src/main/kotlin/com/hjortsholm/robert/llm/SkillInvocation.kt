package com.hjortsholm.robert.llm

data class SkillInvocation(
    val skill: String,
    val parameters: Map<String, Any?>? = null
)