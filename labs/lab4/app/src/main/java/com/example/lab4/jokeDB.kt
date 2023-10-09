package com.example.lab4

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Database(entities= [JokeData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JokeDB : RoomDatabase(){
    abstract fun jokeDao(): JokeDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: JokeDB? = null

        fun getDatabase(context: Context): JokeDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    JokeDB::class.java,
                    "joke_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


@Dao
interface JokeDAO {

    @Insert
    suspend fun addJokeData(data: JokeData)

    @Query("SELECT * from jokes ORDER BY timestamp DESC LIMIT 1")
    fun randomJoke() : Flow<JokeData>

    @Query("SELECT * from jokes ORDER BY timestamp DESC")
    fun allJokes() : Flow<List<JokeData>>

}