package com.lorenzo.stonksnews.api

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.lorenzo.stonksnews.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object YFApiNetwork {
    val moshi = Moshi.Builder().build()

    private val retrofit: Retrofit
        get() {
            val builder = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(YAHOO_FINANCE_HEADER_API_TOKEN_NAME, YAHOO_FINANCE_API_TOKEN)
                        .build()

                    chain.proceed(request)
                }

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(OkHttpProfilerInterceptor())
            }

            val client = builder.build()

            return Retrofit.Builder()
                .baseUrl(YAHOO_FINANCE_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
        }

    val yahooFinance: YFApiService = retrofit.create(YFApiService::class.java)
}