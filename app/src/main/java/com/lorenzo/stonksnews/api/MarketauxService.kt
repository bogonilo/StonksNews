package com.lorenzo.stonksnews.api

import com.lorenzo.stonksnews.model.response.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketauxService {
    @GET("news/all$API_QUERY")
    suspend fun getAllNews(): NewsListResponse
}
