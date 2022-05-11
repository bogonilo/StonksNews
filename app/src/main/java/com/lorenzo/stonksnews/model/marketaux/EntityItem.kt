package com.lorenzo.stonksnews.model.marketaux

import androidx.room.Entity
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class EntityItem constructor(
    val symbol: String,
    val name: String,
    val exchange: String,
    val exchange_long: String,
    val country: String,
    val type: String,
    val industry: String,
    val match_score: Float,
    val sentiment_score: Float,
    val highlights: List<Highlight>
) : Serializable
