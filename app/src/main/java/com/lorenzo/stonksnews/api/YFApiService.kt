package com.lorenzo.stonksnews.api

import com.lorenzo.stonksnews.model.response.yfapi.TrendingSymbolsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface YFApiService {
    @GET("/v1/finance/trending/{region}")
    suspend fun getTrendingSymbols(@Path("region") region: String): TrendingSymbolsResponse
}
