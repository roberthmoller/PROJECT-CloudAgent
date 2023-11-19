package com.hjortsholm.robert.llm.v1.skills

data class SkillInvocation(
    val skill: String,
    val parameters: Map<String, Any?>
)