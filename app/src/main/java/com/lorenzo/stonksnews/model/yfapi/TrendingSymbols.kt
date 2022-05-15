package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.converters.SimpleListConverter
import com.lorenzo.stonksnews.model.response.yfapi.YFApiResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@JsonClass(generateAdapter = true)
class TrendingSymbols(
    val count: Int,
    val quotes: List<Quote>,
    val startInterval: String
): YFApiResponse
