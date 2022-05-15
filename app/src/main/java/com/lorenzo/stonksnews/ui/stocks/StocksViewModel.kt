package com.lorenzo.stonksnews.ui.stocks

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.userPreferencesDataStore
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols
import com.lorenzo.stonksnews.repository.StocksRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class StocksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StocksRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    val errorLimitReached = repository.errorLimitReached

    val regions = repository.regions

    val selectedRegion = repository.getUserRegionFromPreferencesStore()

    var stockHistory = MutableLiveData<List<StockHistory>?>()

    var trendingSymbols = MutableLiveData<RegionQuotesDb>()

    private suspend fun setTrendingSymbols(selectedRegion: String) {
        trendingSymbols.value = repository.getRegionQuotes(selectedRegion)
    }

    fun loadTrendingSymbols(region: String) {
        viewModelScope.launch {
            try {
                repository.loadSymbols(region)
            } catch (error: IOException) {
                Log.e("StonksNews", error.message ?: "error executing loadSymbols")
            } finally {
                setTrendingSymbols(region)
            }
        }
    }

    fun loadStockValues(symbols: List<String>) {
        viewModelScope.launch {
            try {
                repository.loadStockHistory(symbols.joinToString(","))
            } catch (error: IOException) {
                Log.e("StonksNews", error.message ?: "error executing loadSymbols")
            } finally {
                stockHistory.value = repository.getStocksHistory(symbols)
            }
        }
    }

    fun saveUserRegionToPreferencesStore(region: String) {
        viewModelScope.launch {
            repository.saveUserRegionToPreferencesStore(region)
        }
    }
}