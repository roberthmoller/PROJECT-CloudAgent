package com.hjortsholm.robert.llm.spec.v1.external.advice

data class AdviceSearchResult(
    val total_results: Int,
    val query: String,
    val slips: List<Advice>

)