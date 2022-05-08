package com.lorenzo.stonksnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.EntityItemListConverter
import com.lorenzo.stonksnews.database.NewsItemListConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
@TypeConverters(EntityItemListConverter::class, NewsItemListConverter::class)
data class NewsItem constructor(
    @PrimaryKey val uuid: String,
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
