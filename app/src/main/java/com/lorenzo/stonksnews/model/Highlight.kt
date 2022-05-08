package com.lorenzo.stonksnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Highlight constructor(
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
