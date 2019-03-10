package com.jedrula.moviegeek.data.repository

import androidx.lifecycle.LiveData
import com.jedrula.moviegeek.data.db.entity.Movie

interface MovieRepository {
    suspend fun  getMovie(movieId: Int): LiveData<Movie>
}