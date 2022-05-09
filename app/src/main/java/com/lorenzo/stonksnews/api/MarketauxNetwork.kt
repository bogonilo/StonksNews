package com.lorenzo.stonksnews.api

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.lorenzo.stonksnews.BuildConfig
import com.lorenzo.stonksnews.database.converters.DateTypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object MarketauxNetwork {
    val moshi = Moshi.Builder()
        .add(Date::class.java, DateTypeConverter.DateTypeAdapter())
        .build()

    private val retrofit: Retrofit
    get() {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    val marketaux: MarketauxService = retrofit.create(MarketauxService::class.java)
}
