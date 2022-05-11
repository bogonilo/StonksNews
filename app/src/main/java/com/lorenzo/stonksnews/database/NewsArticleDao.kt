package com.lorenzo.stonksnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lorenzo.stonksnews.model.ArticleBody

@Dao
interface NewsArticleDao {
    @Query("select * from articlebody where url is :articleUrl limit 1")
    fun getArticle(articleUrl: String): LiveData<ArticleBody?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleBody: ArticleBody)
}
