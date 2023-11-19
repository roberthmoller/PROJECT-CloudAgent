package com.hjortsholm.robert.spec.v1.knowledge.wikipedia

data class WikipediaPage(
    val pageid: Int,
    val ns: Int,
    val title: String,
    val extract: String
)