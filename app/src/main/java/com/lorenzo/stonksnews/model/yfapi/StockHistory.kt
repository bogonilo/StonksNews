package com.lorenzo.stonksnews.model.yfapi

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lorenzo.stonksnews.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.NumberFormat

@Entity
@JsonClass(generateAdapter = true)
data class StockHistory(
    @PrimaryKey val symbol: String,
    val timestamp: List<Long>,
    @Json(name = "close") val values: List<Float?>
) {
    val change: Float
    get() {
        val firstValue = values.firstOrNull { it != null } ?: return 0f
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
