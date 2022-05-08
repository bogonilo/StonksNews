package com.lorenzo.stonksnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lorenzo.stonksnews.model.NewsItem

@Database(entities = [NewsItem::class], version = 1)
abstract class StonksDatabase : RoomDatabase() {
    abstract val newsListDao: NewsListDao

    companion object {
        private const val DATABASE_NAME = "stonks_db"

        private lateinit var INSTANCE: StonksDatabase

        fun getDatabase(context: Context): StonksDatabase {
            synchronized(StonksDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StonksDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }

            return INSTANCE
        }
    }
}
