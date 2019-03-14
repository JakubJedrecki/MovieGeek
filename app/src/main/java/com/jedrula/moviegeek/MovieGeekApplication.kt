package com.jedrula.moviegeek

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jedrula.moviegeek.data.db.MoviesDatabase
import com.jedrula.moviegeek.data.network.*
import com.jedrula.moviegeek.data.network.implementations.ConnectivityInterceptorImpl
import com.jedrula.moviegeek.data.network.implementations.MovieNetworkDataSourceImpl
import com.jedrula.moviegeek.data.network.interfaces.ConnectivityInterceptor
import com.jedrula.moviegeek.data.network.interfaces.MovieNetworkDataSource
import com.jedrula.moviegeek.data.repository.MovieRepository
import com.jedrula.moviegeek.data.repository.MovieRepositoryImpl
import com.jedrula.moviegeek.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieGeekApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieGeekApplication))

        bind() from singleton { MoviesDatabase(instance()) }
        bind() from singleton { instance<MoviesDatabase>().movieDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TmdbApiService(instance()) }
        bind<MovieNetworkDataSource>() with singleton { MovieNetworkDataSourceImpl(instance()) }
        bind<MovieRepository>() with singleton { MovieRepositoryImpl(instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}