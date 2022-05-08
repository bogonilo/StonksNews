package com.lorenzo.stonksnews.repository

import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.model.response.NewsListResponse

class NewsListRepository() {
    suspend fun getAllNews(): NewsListResponse {
        return MarketauxNetwork.marketaux.getAllNews()
    }
}