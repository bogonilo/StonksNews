package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lorenzo.stonksnews.database.converters.SimpleListConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.NumberFormat

@Entity
@JsonClass(generateAdapter = true)
@TypeConverters(
    SimpleListConverter.LongListConverter::class,
    SimpleListConverter.FloatListConverter::class)
data class StockHistory(
    @PrimaryKey val symbol: String,
    val timestamp: List<Long?>?,
    @Json(name = "close") val values: List<Float?>?
) {
    val change: Float
    get() {
        val firstValue = values?.firstOrNull { it != null } ?: return 0f
        val lastValue = values.lastOrNull { it != null } ?: return 0f

        return (lastValue - firstValue)/firstValue
    }

    val changePercentage: String
    get() {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = 2
        return formatter.format(change * 100) + " %"
    }
}
