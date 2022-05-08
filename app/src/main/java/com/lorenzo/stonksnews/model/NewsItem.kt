package com.lorenzo.stonksnews.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NewsItem(
    val uuid: String,
    val title: String,
    val description: String,
    val keywords: String,
    val snippet: String,
    val url: String,
    val image_url: String,
    val language: String,
    val published_at: String,
    val source: String,
    val relevance_score: Float?,
    val entities: List<EntityItem>,
    val similar: List<NewsItem>?
)
