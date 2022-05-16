package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.database.userPreferencesDataStore
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.repository.NewsListRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsListRepository(
        StonksDatabase.getDatabase(application),
        application.userPreferencesDataStore
    )

    val allNews: LiveData<List<NewsItem>> = repository.news

    val errorLimitReached = MutableLiveData<Boolean>(false)

    private val _favoriteSymbols = repository.getFavoriteSymbols()
    val favoriteSymbols: LiveData<List<FavoriteSymbol>> = _favoriteSymbols

    private val _filterNewsBySymbol = repository.getNewsFilterFromPreferencesStore()
    val filterNewsBySymbol: LiveData<Boolean> = _filterNewsBySymbol

    fun refreshNews(
        filterBySymbol: Boolean = false,
        favoriteSymbols: List<FavoriteSymbol> = emptyList()
    ) {
        viewModelScope.launch {
            try {
                repository.refreshNews(filterBySymbol, favoriteSymbols)
            } catch (error: Exception) {
                if (error is HttpException && error.code() == 429) {
                    errorLimitReached.value = true
                } else if(error is IOException) {
                    Log.e("StonksNews", error.message ?: "error executing refreshNews")
                }
            }
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsListViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
