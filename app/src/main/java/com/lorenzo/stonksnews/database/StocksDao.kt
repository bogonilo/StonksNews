package com.lorenzo.stonksnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory

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