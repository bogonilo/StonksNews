package com.lorenzo.stonksnews.repository

import androidx.lifecycle.LiveData
import com.lorenzo.stonksnews.api.ArticleFetcher
import com.lorenzo.stonksnews.database.StonksDatabase
import com.lorenzo.stonksnews.model.ArticleBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleBodyRepository(private val database: StonksDatabase) {
    fun getArticle(articleUrl: String?): LiveData<ArticleBody?>? =
        articleUrl?.let { database.articleBodyDao.getArticle(it) }

    suspend fun refreshArticle(articleUrl: String) {
        withContext(Dispatchers.IO) {
            val articleBody = ArticleFetcher.run(articleUrl)
            articleBody?.let {
                database.articleBodyDao.insertArticle(ArticleBody(articleUrl, it))
            }
        }
    }
}
