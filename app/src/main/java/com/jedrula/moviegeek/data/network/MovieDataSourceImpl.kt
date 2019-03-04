package com.jedrula.moviegeek.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jedrula.moviegeek.data.db.entity.Movie
import com.jedrula.moviegeek.internal.NoConnectivityException

class MovieDataSourceImpl(
    private val tmdbApiService: TmdbApiService
) : MovieDataSource {

    private val _downloadedMovie = MutableLiveData<Movie>()
    override val downloadedMovie: LiveData<Movie>
        get() = _downloadedMovie

    override suspend fun fetchMovie(movieId: Int) {
        try {
            val fetchedMovie = tmdbApiService
                .getMovie(movieId)
                .await()

            _downloadedMovie.postValue(fetchedMovie)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity: ", "No internet connection.", e)
        }
    }
}