package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lorenzo.stonksnews.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class StockHistory(
    @PrimaryKey val symbol: String,
    val timestamp: List<Long>,
    @Json(name = "close") val values: List<Float>
) {
    val change: Float
    get() = (values.last() - values.first())/values.first()

    val changePercentage: String
    get() = change.toString()


    fun toArrowDrawable(): Int {
        return if (change > 0) {
            R.drawable.ic_baseline_keyboard_arrow_up_24
        } else {
            R.drawable.ic_baseline_keyboard_arrow_down_24
        }
    }
}
