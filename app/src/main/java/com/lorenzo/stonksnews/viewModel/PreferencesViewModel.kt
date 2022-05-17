package com.lorenzo.stonksnews.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.userPreferencesDataStore
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.repository.StocksRepository
import kotlinx.coroutines.launch

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StocksRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    val favoriteSymbols: LiveData<List<FavoriteSymbol>> = repository.favoriteSymbols

    val filterNewsBySymbol = repository.getNewsFilterFromPreferencesStore()

    fun insertFavoriteSymbol(symbol: String) {
        viewModelScope.launch {
            repository.insertNewFavoriteSymbol(symbol)
        }
    }

    fun removeFavorite(favoriteSymbol: FavoriteSymbol) {
        viewModelScope.launch {
            repository.removeFavorite(favoriteSymbol)
        }
    }

    fun setNewsFilterPreference(filterByFavorite: Boolean) {
        viewModelScope.launch {
            repository.saveNewsFilterToPreferencesStore(filterByFavorite)
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PreferencesViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
