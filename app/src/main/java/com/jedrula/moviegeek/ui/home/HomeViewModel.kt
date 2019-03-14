package com.jedrula.moviegeek.ui.home

import androidx.lifecycle.ViewModel
import com.jedrula.moviegeek.data.repository.MovieRepository
import com.jedrula.moviegeek.internal.lazyDeferred

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movie by lazyDeferred {
        movieRepository.getMovie(550)
    }
}
