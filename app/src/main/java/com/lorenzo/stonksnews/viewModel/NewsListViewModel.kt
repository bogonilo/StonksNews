package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.repository.NewsListRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsListRepository(StonksDatabase.getDatabase(application))

    val allNews: LiveData<List<NewsItem>>
    get() {
        viewModelScope.launch {
            try {
                repository.refreshNews()
            }  catch (error: Exception) {
                if (error is HttpException && error.code() == 429) {
                    errorLimitReached.value = true
                } else if(error is IOException) {
                    Log.e("StonksNews", error.message ?: "error executing refreshNews")
                }
            }
        }

        return repository.news
    }

    val errorLimitReached = MutableLiveData<Boolean>(false)

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
