package com.hjortsholm.robert.llm.skills.external.jokes.chucknorris

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class ChuckNorrisJoke @JsonCreator constructor(
    @JsonProperty("categories") val categories: List<String>? = Collections.emptyList(),
    @JsonProperty("icon_url") val iconUrl: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("updated_at") val updatedAt: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("value") val value: String,
)