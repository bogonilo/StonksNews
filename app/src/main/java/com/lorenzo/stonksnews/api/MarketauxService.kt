package com.lorenzo.stonksnews.api

import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.model.response.marketaux.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketauxService {
    @GET("news/all")
    suspend fun getAllNews(): NewsListResponse

    @GET("news/uuid/{uuid}")
    suspend fun getNews(@Query("uuid") uuid: String): NewsItem
}
