package com.lorenzo.stonksnews.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lorenzo.stonksnews.api.YFApiNetwork
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.USER_REGION_SELECTED
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.util.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StocksRepository(
    private val database: StonksDatabase,
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private const val REGION_US = "US"

        private const val REGION_AU = "AU"

        private const val REGION_CA = "CA"

        private const val REGION_FR = "FR"

        private const val REGION_DE = "DE"

        private const val REGION_HK = "HK"

        private const val REGION_IT = "IT"

        private const val REGION_ES = "ES"

        private const val REGION_GB = "GB"

        private const val REGION_IN = "IN"
    }

    val favoriteSymbols = database.favoriteSymbolsDao.getSymbols()

    val regions = listOf(REGION_AU, REGION_CA, REGION_DE, REGION_ES, REGION_FR,
        REGION_GB, REGION_HK, REGION_IN, REGION_IT, REGION_US)

    suspend fun getStocksHistory(symbols: List<String>): List<StockHistory>? {
        return withContext(Dispatchers.IO) {
            database.stockHistoryDao.getStocksHistory(symbols)
        }
    }

    suspend fun getRegionQuotes(region: String): RegionQuotesDb {
        return withContext(Dispatchers.IO) {
            database.regionQuotesDao.getSymbols(region)
        }
    }

    fun getUserRegionFromPreferencesStore(): LiveData<String?> =
        dataStore.data.map { preferences ->
            preferences[USER_REGION_SELECTED]
        }.asLiveData()

    suspend fun insertNewFavoriteSymbol(symbol: String) {
        withContext(Dispatchers.IO) {
            database.favoriteSymbolsDao.insert(FavoriteSymbol(symbol))
        }
    }

    suspend fun loadSymbols(region: String) {
        withContext(Dispatchers.IO) {
            val response = YFApiNetwork.yahooFinance.getTrendingSymbols(region)
            response.finance.result.toDatabaseModel(region)?.let {
                database.regionQuotesDao.insertAll(it)
            }
        }
    }

    suspend fun loadStockHistory(symbols: String) {
        withContext(Dispatchers.IO) {
            val stocksMap = YFApiNetwork.yahooFinance.getSymbolsValues(symbols)
            database.stockHistoryDao.insertAll(stocksMap.values.toList())
        }
    }

    suspend fun removeFavorite(favoriteSymbol: FavoriteSymbol) {
        withContext(Dispatchers.IO) {
            database.favoriteSymbolsDao.removeFavorite(favoriteSymbol)
        }
    }

    suspend fun saveUserRegionToPreferencesStore(region: String) {
        dataStore.edit { preferences ->
            preferences[USER_REGION_SELECTED] = region
        }
    }
}