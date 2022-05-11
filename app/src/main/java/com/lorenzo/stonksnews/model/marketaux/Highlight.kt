package com.lorenzo.stonksnews.model.marketaux

import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class Highlight constructor(
    val highlight: String,
    val sentiment: Float,
    val highlighted_in: HighlightedIn
): Serializable

enum class HighlightedIn {
    @Json(name = "main_text")
    MAIN_TEXT,

    @Json(name = "title")
    TITLE
}
