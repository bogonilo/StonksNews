package com.lorenzo.stonksnews.model.yfapi

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Quote(val symbol: String)
