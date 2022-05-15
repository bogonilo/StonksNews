package com.lorenzo.stonksnews.database

import androidx.annotation.IntegerRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.lorenzo.stonksnews.database.converters.SimpleListConverter
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols

interface StocksDao {
    @Dao
    interface FavoriteSymbols {
        @Query("select * from favoritesymbol")
        fun getSymbols(): LiveData<List<FavoriteSymbol>>

        @Insert(onConflict = REPLACE)
        fun insert(symbol: FavoriteSymbol)

        @Delete
        fun removeFavorite(item: FavoriteSymbol)
    }

    @Dao
    interface RegionQuotesDao {
        @Query("select * from regionquotesdb where region=:region")
        fun getSymbols(region: String): RegionQuotesDb

        @Insert(onConflict = REPLACE)
        fun insertAll(quotes: RegionQuotesDb)
    }

    @Dao
    interface StockHistoryDao {
        @Query("select * from stockhistory where symbol in (:symbols)")
        fun getStocksHistory(symbols: List<String>): List<StockHistory>?

        @Insert(onConflict = REPLACE)
        fun insertAll(symbols: List<StockHistory>)
    }
}