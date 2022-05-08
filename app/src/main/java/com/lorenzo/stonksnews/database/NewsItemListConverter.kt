package com.lorenzo.stonksnews.database


import androidx.room.TypeConverter
import com.lorenzo.stonksnews.model.NewsItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class NewsItemListConverter {
    private val newsType = Types.newParameterizedType(List::class.java, NewsItem::class.java)

    private val moshi = Moshi.Builder().build()

    private val newsAdapter = moshi.adapter<List<NewsItem>>(newsType)

    @TypeConverter
    fun stringToNewsItems(string: String): List<NewsItem> {
        return newsAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun newsToString(members: List<NewsItem>): String {
        return newsAdapter.toJson(members)
    }
}