package com.jedrula.moviegeek.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jedrula.moviegeek.data.db.dao.MovieDao
import com.jedrula.moviegeek.data.db.entity.Genre
import com.jedrula.moviegeek.data.db.entity.Movie
import com.jedrula.moviegeek.data.db.typeconverters.GenreConverter

@Database(
    entities = [Movie::class, Genre::class],
    version = 1
)
@TypeConverters(GenreConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java, "Movies.db")
                .build()
    }
}