package com.lorenzo.stonksnews.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityItem(
    val symbol: String,
    val name: String,
    val exchange: String,
    val exchange_long: String,
    val country: String,
    val type: String,
    val industry: String,
    val match_score: Float,
    val sentiment_score: Float,
    val highlights: List<Highlight>
)
