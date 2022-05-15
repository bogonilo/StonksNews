package com.lorenzo.stonksnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lorenzo.stonksnews.database.converters.SimpleListConverter
import com.lorenzo.stonksnews.model.ArticleBody
import com.lorenzo.stonksnews.model.FavoriteSymbol
import com.lorenzo.stonksnews.model.marketaux.NewsItem
import com.lorenzo.stonksnews.model.yfapi.RegionQuotesDb
import com.lorenzo.stonksnews.model.yfapi.StockHistory
import com.lorenzo.stonksnews.model.yfapi.TrendingSymbols

@Database(entities = [
    NewsItem::class,
    ArticleBody::class,
    StockHistory::class,
    RegionQuotesDb::class,
    FavoriteSymbol::class], version = StonksDatabase.DATABASE_LAST_VERSION)
abstract class StonksDatabase : RoomDatabase() {
    abstract val articleBodyDao: NewsArticleDao

    abstract val favoriteSymbolsDao: StocksDao.FavoriteSymbols

    abstract val newsListDao: NewsListDao

    abstract val regionQuotesDao: StocksDao.RegionQuotesDao

    abstract val stockHistoryDao: StocksDao.StockHistoryDao

    companion object {
        private const val DATABASE_NAME = "stonks_db"

        private const val DATABASE_VERSION_FIRST = 1

        private const val DATABASE_VERSION_SECOND = 2

        private const val DATABASE_VERSION_THREE = 3

        private const val DATABASE_VERSION_FOUR = 4

        private const val DATABASE_VERSION_FIVE = 5

        private const val DATABASE_VERSION_SIX = 6

        private const val DATABASE_VERSION_SEVEN = 7

        const val DATABASE_LAST_VERSION = DATABASE_VERSION_SEVEN

        private lateinit var INSTANCE: StonksDatabase

        fun getDatabase(context: Context): StonksDatabase {
            synchronized(StonksDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StonksDatabase::class.java,
                        DATABASE_NAME
                    ).addMigrations(Migration_1_2)
                        .fallbackToDestructiveMigration()
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
