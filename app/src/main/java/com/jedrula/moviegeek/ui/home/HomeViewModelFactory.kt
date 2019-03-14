package com.jedrula.moviegeek.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jedrula.moviegeek.data.repository.MovieRepository

class HomeViewModelFactory(
    private val movieRepository: MovieRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(movieRepository) as T
    }

}