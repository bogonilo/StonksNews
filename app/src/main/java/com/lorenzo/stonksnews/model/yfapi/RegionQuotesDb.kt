package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.converters.SimpleListConverter

@Entity
@TypeConverters(SimpleListConverter.QuoteItemListConverter::class)
data class RegionQuotesDb(
    @PrimaryKey val region: String,
    val quotes: List<Quote>
)
