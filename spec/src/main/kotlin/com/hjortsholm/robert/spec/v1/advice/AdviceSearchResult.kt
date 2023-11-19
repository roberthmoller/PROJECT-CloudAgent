package com.hjortsholm.robert.spec.v1.advice

data class AdviceSearchResult(
    val total_results: Int,
    val query: String,
    val slips: List<Advice>

)