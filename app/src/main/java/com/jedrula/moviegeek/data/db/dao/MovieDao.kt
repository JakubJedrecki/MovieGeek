package com.jedrula.moviegeek.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jedrula.moviegeek.data.db.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT * FROM movie where id = :movieId")
    fun getMovie(movieId: Int): LiveData<Movie>

    @Query("SELECT * FROM movie")
    fun getMovies(): LiveData<List<Movie>>
}