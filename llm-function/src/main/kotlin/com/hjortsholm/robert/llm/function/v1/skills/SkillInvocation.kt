package com.hjortsholm.robert.llm.function.v1.skills

data class SkillInvocation(
    val skill: String,
    val parameters: Map<String, Any?>
)