package com.lorenzo.stonksnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lorenzo.stonksnews.model.NewsItem

@Dao
interface NewsListDao {
    @Query("select * from newsitem")
    fun getVideos(): LiveData<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<NewsItem>)
}
