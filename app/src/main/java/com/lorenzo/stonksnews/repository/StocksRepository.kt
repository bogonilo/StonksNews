package com.lorenzo.stonksnews.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.lorenzo.stonksnews.api.YFApiNetwork
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.USER_REGION_SELECTED
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

    val regions = listOf(REGION_AU, REGION_CA, REGION_DE, REGION_ES, REGION_FR,
        REGION_GB, REGION_HK, REGION_IN, REGION_IT, REGION_US)

    val symbols = database.trendingSymbolsDao.getSymbols()

    fun getUserRegionFromPreferencesStore(): LiveData<String?> =
        dataStore.data.map { preferences ->
            preferences[USER_REGION_SELECTED]
        }.asLiveData()

    suspend fun loadSymbols(region: String) {
        withContext(Dispatchers.IO) {
            saveUserRegionToPreferencesStore(region)
            val response = YFApiNetwork.yahooFinance.getTrendingSymbols(region)
            database.trendingSymbolsDao.insertAll(response.finance.result)
        }
    }

    private suspend fun saveUserRegionToPreferencesStore(region: String) {
        dataStore.edit { preferences ->
            preferences[USER_REGION_SELECTED] = region
        }
    }
}