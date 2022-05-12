package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.api.converters.EntityItemListConverter
import com.lorenzo.stonksnews.api.converters.NewsItemListConverter
import com.lorenzo.stonksnews.api.converters.QuoteItemListConverter
import com.lorenzo.stonksnews.model.response.yfapi.YFApiResponse
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@Entity
@JsonClass(generateAdapter = true)
@TypeConverters(QuoteItemListConverter::class)
open class TrendingSymbols(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val count: Int,
    val quotes: List<Quote>,
    val startInterval: String
): YFApiResponse
