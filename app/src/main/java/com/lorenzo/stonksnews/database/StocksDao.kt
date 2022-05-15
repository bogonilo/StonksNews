package com.lorenzo.stonksnews.database

import androidx.annotation.IntegerRes
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols

interface StocksDao {
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