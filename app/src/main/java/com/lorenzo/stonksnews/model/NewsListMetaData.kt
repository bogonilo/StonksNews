package com.lorenzo.stonksnews.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsListMetaData(
    val found: Int,
    val returned: Int,
    val limit: Int,
    val page: Int
)
