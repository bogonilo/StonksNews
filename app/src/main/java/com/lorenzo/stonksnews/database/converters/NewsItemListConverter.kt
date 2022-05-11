package com.lorenzo.stonksnews.database.converters


import androidx.room.TypeConverter
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.squareup.moshi.Types

class NewsItemListConverter {
    private val newsType = Types.newParameterizedType(List::class.java, NewsItem::class.java)

    private val moshi = MarketauxNetwork.moshi

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