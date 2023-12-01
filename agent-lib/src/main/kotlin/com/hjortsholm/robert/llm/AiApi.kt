package com.hjortsholm.robert.llm

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

interface AiApi {
    /**
     * Challenge the agent to complete a goal
     */
    @PostMapping
    fun challenge(@RequestBody goal: String): AiResponse

    /**
     * Return the skills the agent has
     */
    @RequestMapping(method = [RequestMethod.OPTIONS])
    fun skills(): Map<String, Skill>
}