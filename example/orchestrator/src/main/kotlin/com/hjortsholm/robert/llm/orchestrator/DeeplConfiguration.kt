package com.hjortsholm.robert.llm.orchestrator

import com.deepl.api.Translator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeeplConfiguration {
    @Bean
    fun translator(@Value("\${deepl.authKey}") deeplAuthKey: String): Translator = Translator(deeplAuthKey)
}