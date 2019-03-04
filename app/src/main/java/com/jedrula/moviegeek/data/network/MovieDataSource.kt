package com.jedrula.moviegeek.data.network

import androidx.lifecycle.LiveData
import com.jedrula.moviegeek.data.db.entity.Movie

interface MovieDataSource {
    val downloadedMovie: LiveData<Movie>

    suspend fun fetchMovie(
        movieId: Int
    )
}