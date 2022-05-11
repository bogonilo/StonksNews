package com.lorenzo.stonksnews.model.response.marketaux

import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.model.marketaux.NewsListMetaData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsListResponse(val meta: NewsListMetaData, val data: List<NewsItem>)
