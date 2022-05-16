package com.lorenzo.stonksnews.model.yfapi

import com.lorenzo.stonksnews.model.response.yfapi.YFApiResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TrendingSymbols(
    val count: Int,
    val quotes: List<Quote>,
    val startInterval: String
): YFApiResponse
