package com.lorenzo.stonksnews.model.response

import com.lorenzo.stonksnews.model.NewsItem
import com.lorenzo.stonksnews.model.NewsListMetaData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsListResponse(
    val meta: NewsListMetaData,
    val data: List<NewsItem>
)
