package com.lorenzo.stonksnews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.api.YFApiNetwork
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsListRepository(private val database: StonksDatabase) {
    val news: LiveData<List<NewsItem>> = database.newsListDao.getNews()

    suspend fun refreshNews(shouldRefresh: Boolean = true) {
        withContext(Dispatchers.IO) {
            if (shouldRefresh) {
                val newsListResponse = MarketauxNetwork.marketaux.getAllNews()
                database.newsListDao.insertAll(newsListResponse.data)
            }
        }
    }
}