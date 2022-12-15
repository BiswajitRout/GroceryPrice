package com.grocery.groceryprice.db.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    @TypeConverter
    fun fromStringToLong(date: String): Long {
        return try {
            formatter.parse(date)?.time ?: Date().time
        } finally {
            Date().time
        }
    }

    @TypeConverter
    fun fromLongToString(date: Long) : String {
        return formatter.format(Date(date))
    }
}