package com.jedrula.moviegeek.data.repository

import androidx.lifecycle.LiveData
import com.jedrula.moviegeek.data.db.dao.MovieDao
import com.jedrula.moviegeek.data.db.entity.Movie
import com.jedrula.moviegeek.data.network.MovieNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {

    init {
        movieNetworkDataSource.downloadedMovie.observeForever {newMovie ->
             persistFetchedMovie(newMovie)
        }
    }

    override suspend fun getMovie(movieId: Int): LiveData<Movie> {
        return withContext(Dispatchers.IO) {
            initMovieData()
            return@withContext movieDao.getMovie(movieId)
        }
    }

    private fun persistFetchedMovie(fetchedMovie: Movie) {
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.insert(fetchedMovie)
        }
    }

    private suspend fun initMovieData() {
        //todo remove minusdays
        if(isFetchNeeded(ZonedDateTime.now().minusDays(2)))
            fetchMovie(550)
    }

    private suspend fun fetchMovie(movieId: Int) {
        movieNetworkDataSource.fetchMovie(movieId)
    }

    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val dayAgo = ZonedDateTime.now().minusDays(1)
        return lastFetchTime.isBefore(dayAgo)
    }
}