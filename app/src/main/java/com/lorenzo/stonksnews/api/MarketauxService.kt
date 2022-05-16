package com.lorenzo.stonksnews.api

import com.lorenzo.stonksnews.model.response.marketaux.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketauxService {
    @GET("news/all")
    suspend fun getAllNews(
        @Query("symbols") symbols: String? = null
    ): NewsListResponse
}
