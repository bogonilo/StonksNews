package com.lorenzo.stonksnews.api.converters

import androidx.room.TypeConverter
import com.lorenzo.stonksnews.api.MarketauxNetwork
import com.lorenzo.stonksnews.model.marketaux.EntityItem
import com.squareup.moshi.Types

class EntityItemListConverter {
    private val entitiesType = Types.newParameterizedType(List::class.java, EntityItem::class.java)

    private val moshi = MarketauxNetwork.moshi

    private val entitiesAdapter = moshi.adapter<List<EntityItem>>(entitiesType)

    @TypeConverter
    fun stringToEntityItems(string: String): List<EntityItem> {
        return entitiesAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun newsToString(members: List<EntityItem>): String {
        return entitiesAdapter.toJson(members)
    }
}
