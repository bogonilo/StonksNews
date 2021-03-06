package com.lorenzo.stonksnews.database.converters

import androidx.room.TypeConverter
import com.lorenzo.stonksnews.api.moshi
import com.squareup.moshi.*
import java.util.*

class DateTypeConverter {
    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun stringToDate(string: String): Date? {
        return moshi.adapter<Date>().fromJson(string)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun dateToString(date: Date): String {
        return moshi.adapter<Date>().toJson(date)
    }

    class DateTypeAdapter : JsonAdapter<Date>() {
        @ToJson
        override fun toJson(writer: JsonWriter, value: Date?) {
            writer.value(value?.time)
        }

        @FromJson
        override fun fromJson(reader: JsonReader): Date {
            return Date(reader.nextString().toLong())
        }
    }
}
