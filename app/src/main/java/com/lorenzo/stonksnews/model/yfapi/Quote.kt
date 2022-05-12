package com.lorenzo.stonksnews.model.yfapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Quote(@PrimaryKey val symbol: String)
