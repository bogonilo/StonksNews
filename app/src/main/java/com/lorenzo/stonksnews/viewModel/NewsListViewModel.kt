package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lorenzo.stonksnews.repository.NewsListRepository
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {
    private val repository = NewsListRepository()

    fun getAllNews() {
        viewModelScope.launch {
            val news = repository.getAllNews()
            Log.d("Laurentio - getAllNews", news.toString())
        }
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsListViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
