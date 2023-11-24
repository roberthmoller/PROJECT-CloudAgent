package com.hjortsholm.robert.llm.spec.v1.external.knowledge.wikipedia

data class WikipediaPage(
    val pageid: Int,
    val ns: Int,
    val title: String,
    val extract: String
)