package com.hjortsholm.robert.spec.v1.knowledge.wikipedia

data class WikipediaQuery(
    val pages: Map<String, WikipediaPage>
)