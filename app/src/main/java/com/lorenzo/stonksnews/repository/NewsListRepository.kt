package com.lorenzo.stonksnews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsListRepository(private val database: StonksDatabase) {
    val news: LiveData<List<NewsItem>> = database.newsListDao.getVideos()

    suspend fun refreshNews(shouldRefresh: Boolean = true) {
        withContext(Dispatchers.IO) {
            if (shouldRefresh) {
                val newsListResponse = MarketauxNetwork.marketaux.getAllNews()
                database.newsListDao.insertAll(newsListResponse.data)
            }
        }
    }
}