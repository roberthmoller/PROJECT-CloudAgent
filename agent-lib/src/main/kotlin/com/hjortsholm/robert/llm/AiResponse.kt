package com.hjortsholm.robert.llm

data class AiResponse(
    val response: Any,
    val artefacts: List<SkillResult>,
)