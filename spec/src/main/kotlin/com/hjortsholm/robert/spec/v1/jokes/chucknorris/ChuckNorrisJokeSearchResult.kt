package com.hjortsholm.robert.spec.v1.jokes.chucknorris

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckNorrisJokeSearchResult @JsonCreator constructor(
    @JsonProperty("total") val total: Int,
    @JsonProperty("result") val result: List<ChuckNorrisJoke>,
)