package com.lorenzo.stonksnews.ui.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lorenzo.stonksnews.api.YFApiNetwork
import kotlinx.coroutines.launch

class StocksViewModel : ViewModel() {
    fun loadTrendingSymbols() {
        viewModelScope.launch {
            YFApiNetwork.yahooFinance.getTrendingSymbols("US")
        }
    }
}