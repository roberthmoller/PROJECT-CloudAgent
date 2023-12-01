package com.hjortsholm.robert.llm.orchestrator

import com.deepl.api.Translator
import org.springframework.stereotype.Component

@Component
class DeeplClient(private val translator: Translator) {
    fun translate(text: String, twoDigitTargetLanguageCode: String): String {
        return translator.translateText(text, null, twoDigitTargetLanguageCode).text
    }
}