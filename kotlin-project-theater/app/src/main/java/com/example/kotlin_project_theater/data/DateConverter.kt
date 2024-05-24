package com.example.kotlin_project_theater.data

import androidx.room.TypeConverter
import java.util.Date

open class DateConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }
    }
}
