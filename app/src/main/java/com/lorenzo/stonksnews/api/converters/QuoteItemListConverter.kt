package com.lorenzo.stonksnews.api.converters

import androidx.room.TypeConverter
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.model.marketaux.EntityItem
import com.lorenzo.stonksnews.model.yfapi.Quote
import com.squareup.moshi.Types

class QuoteItemListConverter {
    private val entitiesType = Types.newParameterizedType(List::class.java, Quote::class.java)

    private val moshi = MarketauxNetwork.moshi

    private val entitiesAdapter = moshi.adapter<List<Quote>>(entitiesType)

    @TypeConverter
    fun stringToQuoteItem(string: String): List<Quote> {
        return entitiesAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun quotesToString(quotes: List<Quote>): String {
        return entitiesAdapter.toJson(quotes)
    }
}