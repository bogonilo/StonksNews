package com.lorenzo.stonksnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteSymbol(@PrimaryKey val value: String)
