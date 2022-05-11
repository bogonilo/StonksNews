package com.lorenzo.stonksnews.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lorenzo.stonksnews.api.ArticleFetcher
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.ArticleBody
import com.lorenzo.stonksnews.model.NewsItem
import com.lorenzo.stonksnews.repository.ArticleBodyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class NewsDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ArticleBodyRepository(StonksDatabase.getDatabase(application))

    val articleBody: LiveData<ArticleBody?>?
    get() {
        val url = newsItem?.url ?: return null

        viewModelScope.launch {
            try {
                repository.refreshArticle(url)
            } catch (e: IOException) {
                Log.e("StonksNews", e.message.toString())
            }
        }

        return repository.getArticle(url)
    }

    private var _newsItem: NewsItem? = null
    val newsItem: NewsItem?
    get() = _newsItem

    fun setNewsItemSelected(item: NewsItem) {
        _newsItem = item
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
