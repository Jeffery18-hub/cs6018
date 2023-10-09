package com.example.lab4

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.Date

//apparently Room can't handle Date objects directly...
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

//define a sqlite table
@Entity(tableName = "jokes")
data class JokeData(
    var timestamp: Date,
    var content: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}