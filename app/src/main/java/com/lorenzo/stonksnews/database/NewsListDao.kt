package com.lorenzo.stonksnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lorenzo.stonksnews.model.marketaux.NewsItem

@Dao
interface NewsListDao {
    @Query("select * from newsitem")
    fun getNews(): LiveData<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<NewsItem>)
}
