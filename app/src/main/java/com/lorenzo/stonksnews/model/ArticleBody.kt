package com.lorenzo.stonksnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class ArticleBody(
    @PrimaryKey val url: String,
    val body: String
): Serializable
