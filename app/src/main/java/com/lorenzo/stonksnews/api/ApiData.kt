package com.lorenzo.stonksnews.api

import com.squareup.moshi.Moshi

const val MARKETAUX_API_TOKEN = "igfBgnrjqFTNShnpxP7K8moAQPcg2e49b9juOXHp"

//const val YAHOO_FINANCE_API_TOKEN = "wpy1Y4mKy09dS625K1hUW6RVyB4jFfhT46uuqMbn"
const val YAHOO_FINANCE_API_TOKEN = "KzFaNo0eDx18PTS2KKuqe9LnStaIV1AM9p6FU1YE"

const val BASE_LANGUAGE = "en"

const val MARKETAUX_API_QUERY_LANGUAGE = "language"

const val MARKETAUX_API_QUERY_TOKEN = "api_token"

const val MARKETAUX_BASE_URL = "api.marketaux.com"

const val YAHOO_FINANCE_BASE_URL = "https://yfapi.net"

const val YAHOO_FINANCE_HEADER_API_TOKEN_NAME = "X-API-KEY"

val moshi: Moshi = Moshi.Builder().build()
