package com.hjortsholm.robert.llm.spec.v1.external.knowledge.wikipedia

data class WikipediaQuery(
    val pages: Map<String, WikipediaPage>
)