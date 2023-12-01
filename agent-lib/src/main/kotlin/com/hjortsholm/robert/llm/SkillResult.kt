package com.hjortsholm.robert.llm

data class SkillResult(
    val call: SkillInvocation,
    val result: Any
)