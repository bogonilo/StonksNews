package com.lorenzo.stonksnews.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.database.FILTER_NEWS_BY_FAVORITE_SYMBOLS
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NewsListRepository(
    private val database: StonksDatabase,
    private val dataStore: DataStore<Preferences>
) {
    val news: LiveData<List<NewsItem>> = database.newsListDao.getNews()

    fun getNewsFilterFromPreferencesStore(): LiveData<Boolean> =
        dataStore.data.map { preferences ->
            preferences[FILTER_NEWS_BY_FAVORITE_SYMBOLS] ?: false
        }.asLiveData()

    fun getFavoriteSymbols(): LiveData<List<FavoriteSymbol>> {
        return database.favoriteSymbolsDao.getSymbols()
    }

    suspend fun refreshNews(filterBySymbol: Boolean, favoriteSymbols: List<FavoriteSymbol>) {
        withContext(Dispatchers.IO) {
            val newsListResponse = if (filterBySymbol) {
                MarketauxNetwork.marketaux.getAllNews(
                    favoriteSymbols.joinToString(",") { it.value }
                )
            } else {
                MarketauxNetwork.marketaux.getAllNews()
            }

            database.newsListDao.insertAll(newsListResponse.data)
        }
    }
}