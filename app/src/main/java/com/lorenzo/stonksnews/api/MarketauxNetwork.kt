package com.lorenzo.stonksnews.api

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.lorenzo.stonksnews.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object MarketauxNetwork {
    private val retrofit: Retrofit
    get() {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder()
                .addQueryParameter(MARKETAUX_API_QUERY_TOKEN, MARKETAUX_API_TOKEN)
                .addQueryParameter(MARKETAUX_API_QUERY_LANGUAGE, BASE_LANGUAGE)
                .build()
            request = request.newBuilder().url(url).build()

            chain.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        val client =builder.build()

        val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host(MARKETAUX_BASE_URL)
            .addPathSegments("v1/")
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    val marketaux: MarketauxService = retrofit.create(MarketauxService::class.java)
}
