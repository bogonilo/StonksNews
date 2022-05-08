package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.NewsItem
import com.lorenzo.stonksnews.repository.NewsListRepository
import kotlinx.coroutines.launch
import java.io.IOException

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsListRepository(StonksDatabase.getDatabase(application))

    val allNews: LiveData<List<NewsItem>>
    get() {
        viewModelScope.launch {
            try {
                repository.refreshNews()
            } catch (error: IOException) {
                Log.e("StonksNews", error.message ?: "error executing refreshNews")
            }
        }

        return repository.news
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
