package com.lorenzo.stonksnews.model

import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.converters.EntityItemListConverter
import com.lorenzo.stonksnews.database.converters.NewsItemListConverter
import com.squareup.moshi.JsonClass
import java.io.Serializable
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
): Serializable {
    val formattedDate: String
    get() = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
            .format(timeStampMillis)

    val formattedSourceAndDate: String
    get() {
        return "$formattedDate, $source"
    }

    val spannedUrl: Spanned
    get() {
        val htmlText = "<a href=\"$url\">$url</a>"

        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }


    val spannedDescription: Spanned
    get() {
        return HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    val timeStampMillis: Long?
    get() {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = sdf.parse(published_at)

        return date?.time
    }
}
