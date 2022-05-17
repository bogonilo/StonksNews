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

class StocksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StocksRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    private val _errorLimitReached = MutableLiveData<Boolean>(false)
    val errorLimitReached: LiveData<Boolean> = _errorLimitReached

    val regions = repository.regions

    val selectedRegion = repository.getUserRegionFromPreferencesStore()

    private val _stockHistory = MutableLiveData<List<StockHistory>?>()
    val stockHistory: LiveData<List<StockHistory>?> = _stockHistory

    private val _trendingSymbols = MutableLiveData<RegionQuotesDb>()
    val trendingSymbols: LiveData<RegionQuotesDb> = _trendingSymbols

    private suspend fun setTrendingSymbols(selectedRegion: String) {
        _trendingSymbols.value = repository.getRegionQuotes(selectedRegion)
    }

    fun loadTrendingSymbols(region: String) {
        viewModelScope.launch {
            try {
                repository.loadSymbols(region)
            } catch (error: Exception) {
                if (error is HttpException && error.code() == 429) {
                    _errorLimitReached.value = true
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
                    _errorLimitReached.value = true
                } else if(error is IOException) {
                    Log.e("StonksNews", error.message ?: "error executing loadStockValues")
                }
            } finally {
                _stockHistory.value = repository.getStocksHistory(symbols)
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
