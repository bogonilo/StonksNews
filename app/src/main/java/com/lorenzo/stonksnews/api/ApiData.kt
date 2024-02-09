package com.lorenzo.stonksnews.api

import com.squareup.moshi.Moshi

const val MARKETAUX_API_TOKEN = "ADD_TOKEN"

const val YAHOO_FINANCE_API_TOKEN = "ADD_TOKEN"

const val BASE_LANGUAGE = "en"

const val MARKETAUX_API_QUERY_LANGUAGE = "language"

const val MARKETAUX_API_QUERY_TOKEN = "api_token"

const val MARKETAUX_BASE_URL = "api.marketaux.com"

const val YAHOO_FINANCE_BASE_URL = "https://yfapi.net"

const val YAHOO_FINANCE_HEADER_API_TOKEN_NAME = "X-API-KEY"

val moshi: Moshi = Moshi.Builder().build()
