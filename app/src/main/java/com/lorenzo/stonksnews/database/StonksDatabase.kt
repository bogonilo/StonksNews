package com.lorenzo.stonksnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lorenzo.stonksnews.model.ArticleBody
import com.lorenzo.stonksnews.model.NewsItem

@Database(entities = [NewsItem::class, ArticleBody::class], version = StonksDatabase.DATABASE_LAST_VERSION)
abstract class StonksDatabase : RoomDatabase() {
    abstract val articleBodyDao: NewsArticleDao

    abstract val newsListDao: NewsListDao

    companion object {
        private const val DATABASE_NAME = "stonks_db"

        private const val DATABASE_VERSION_FIRST = 1

        private const val DATABASE_VERSION_SECOND = 2

        const val DATABASE_LAST_VERSION = DATABASE_VERSION_SECOND

        private lateinit var INSTANCE: StonksDatabase

        fun getDatabase(context: Context): StonksDatabase {
            synchronized(StonksDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StonksDatabase::class.java,
                        DATABASE_NAME
                    ).addMigrations(Migration_1_2)
                        .build()
                }
            }

            return INSTANCE
        }
    }

    object Migration_1_2 : Migration(DATABASE_VERSION_FIRST, DATABASE_VERSION_SECOND) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `ArticleBody` (`url` TEXT NOT NULL, `body` TEXT NOT NULL, " +
                    "PRIMARY KEY(`url`))")
        }
    }
}
