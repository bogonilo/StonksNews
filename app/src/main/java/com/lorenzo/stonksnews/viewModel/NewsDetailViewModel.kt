package com.lorenzo.stonksnews.viewModel

import androidx.lifecycle.ViewModel
import com.lorenzo.stonksnews.model.NewsItem

class NewsDetailViewModel : ViewModel() {
    private var _newsItem: NewsItem? = null
    val newsItem: NewsItem?
    get() = _newsItem

    fun setNewsItemSelected(item: NewsItem) {
        _newsItem = item
    }
}
