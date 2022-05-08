package com.lorenzo.stonksnews.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Highlight(
    val highlight: String,
    val sentiment: Float,
    val highlighted_in: HighlightedIn
)

enum class HighlightedIn {
    @Json(name = "main_text")
    MAIN_TEXT,

    @Json(name = "title")
    TITLE
}
