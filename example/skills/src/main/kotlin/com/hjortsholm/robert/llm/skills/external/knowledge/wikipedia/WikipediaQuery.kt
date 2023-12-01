package com.hjortsholm.robert.llm.skills.external.knowledge.wikipedia

data class WikipediaQuery(
    val pages: Map<String, WikipediaPage>
)