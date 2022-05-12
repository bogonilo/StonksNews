package com.lorenzo.stonksnews.api

import com.lorenzo.stonksnews.model.response.yfapi.TrendingSymbolsResponse
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YFApiService {
    @GET("/v8/finance/spark")
    suspend fun getSymbolsValues(
        @Query("symbols") symbols: String,
        @Query("interval") interval: String? = null,
        @Query("range") range: String? = null
    ): Map<String, StockHistory>

    @GET("/v1/finance/trending/{region}")
    suspend fun getTrendingSymbols(@Path("region") region: String): TrendingSymbolsResponse
}
