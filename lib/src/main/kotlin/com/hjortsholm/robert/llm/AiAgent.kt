package com.hjortsholm.robert.llm

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.reflect.KFunction

interface AiAgent {
    val name: String
    val skills: List<KFunction<Any>>

    @PostMapping("/ask")
    fun ask(@RequestBody prompt: String): Any {
        return "Not implemented"
    }

    @RequestMapping("/skills")
    fun listSkills(): List<Skill> {
        return emptyList()
    }
}