package com.lorenzo.stonksnews.ui.stocks

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.userPreferencesDataStore
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols
import com.lorenzo.stonksnews.repository.StocksRepository
import kotlinx.coroutines.launch

class StocksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StocksRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    val regions = repository.regions

    val selectedRegion = repository.getUserRegionFromPreferencesStore()

    var trendingSymbols: LiveData<TrendingSymbols?> = Transformations.map(repository.symbols) {
        it.firstOrNull()
    }

    fun loadTrendingSymbols(region: String) {
        viewModelScope.launch {
            repository.loadSymbols(region)
        }
    }

    fun loadStockValues(symbols: String, onResponse: (Map<String, StockHistory>) -> Unit) {
        viewModelScope.launch {
            val response = repository.loadStockHistory(symbols)
            onResponse(response)
        }
    }
}