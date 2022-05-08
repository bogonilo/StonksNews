package com.lorenzo.stonksnews.api

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.lorenzo.stonksnews.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MarketauxNetwork {
    private val retrofit: Retrofit
    get() {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    val marketaux: MarketauxService = retrofit.create(MarketauxService::class.java)
}
