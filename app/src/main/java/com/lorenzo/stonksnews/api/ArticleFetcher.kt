package com.lorenzo.stonksnews.api

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception


object ArticleFetcher {
    var client = OkHttpClient()

    @Throws(Exception::class)
    fun run(url: String): String? {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request)
            .execute()
            .use { response ->
                return response.body()?.string()
            }
    }
}