package com.lorenzo.stonksnews.database

import androidx.annotation.IntegerRes
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols

interface StocksDao {
    @Dao
    interface TrendingSymbolsDao {
        @Query("select * from trendingsymbols")
        fun getSymbols(): LiveData<List<TrendingSymbols>>

        @Insert(onConflict = REPLACE)
        fun insertAll(symbols: List<TrendingSymbols>)
    }
}