package com.lorenzo.stonksnews.model.response.yfapi

import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TrendingSymbolsResponse(override val finance: FinanceResponseWrapper<TrendingSymbols>) :
    BaseYFApiResponse<TrendingSymbols>(finance)
