package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.userPreferencesDataStore
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.repository.StocksRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class StocksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StocksRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    val errorLimitReached = MutableLiveData<Boolean>(false)

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
            } catch (error: Exception) {
                if (error is HttpException && error.code() == 429) {
                    errorLimitReached.value = true
                } else if(error is IOException) {
                    Log.e("StonksNews", error.message ?: "error executing loadTrendingSymbols")
                }
            } finally {
                setTrendingSymbols(region)
            }
        }
    }

    fun loadStockValues(symbols: List<String>) {
        viewModelScope.launch {
            try {
                repository.loadStockHistory(symbols.joinToString(","))
            } catch (error: Exception) {
                if (error is HttpException && error.code() == 429) {
                    errorLimitReached.value = true
                } else if(error is IOException) {
                    Log.e("StonksNews", error.message ?: "error executing loadStockValues")
                }
            }finally {
                stockHistory.value = repository.getStocksHistory(symbols)
            }
        }
    }

    fun saveUserRegionToPreferencesStore(region: String) {
        viewModelScope.launch {
            repository.saveUserRegionToPreferencesStore(region)
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StocksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StocksViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}