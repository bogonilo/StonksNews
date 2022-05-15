package com.lorenzo.stonksnews.database.converters

import androidx.room.TypeConverter
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.api.YFApiNetwork
import com.lorenzo.stonksnews.api.moshi
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.marketaux.EntityItem
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.model.yfapi.Quote
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

open class SimpleListConverter<T>(private val type: Type) {
    private val entitiesType = Types.newParameterizedType(List::class.java, type)

    private val entitiesAdapter = moshi.adapter<List<T>>(entitiesType)

    @TypeConverter
    fun stringToItem(string: String): List<T> {
        return entitiesAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun itemToString(list: List<T?>?): String {
        return entitiesAdapter.toJson(list?.mapNotNull { it })
    }

    class EntityItemListConverter : SimpleListConverter<EntityItem>(EntityItem::class.java)

    class FloatListConverter : SimpleListConverter<Float>(Float::class.javaObjectType)

    class LongListConverter : SimpleListConverter<Long>(Long::class.javaObjectType)

    class NewsItemListConverter : SimpleListConverter<NewsItem>(NewsItem::class.javaObjectType)

    class QuoteItemListConverter : SimpleListConverter<Quote>(Quote::class.java)
}
