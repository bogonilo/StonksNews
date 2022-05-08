package com.lorenzo.stonksnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.EntityItemListConverter
import com.lorenzo.stonksnews.database.NewsItemListConverter
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

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
) {
    val formattedSourceAndDate: String
    get() {
        val date = SimpleDateFormat("HH:mm dd/MM/yy", Locale.getDefault())
            .format(timeStampMillis)

        return "$date, $source"
    }

    val timeStampMillis: Long?
    get() {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = sdf.parse(published_at)

        return date?.time
    }
}
