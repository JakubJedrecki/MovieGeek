package com.jedrula.moviegeek.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jedrula.moviegeek.R
import com.jedrula.moviegeek.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        bindUI()
//        val tmdbApiService = TmdbApiService(
//            ConnectivityInterceptorImpl(
//                this.context!!
//            )
//        )
//        val movieDataSource =
//            MovieNetworkDataSourceImpl(tmdbApiService)
//
//        movieDataSource.downloadedMovie.observe(this, Observer {
//            home_tv.text = it.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            movieDataSource.fetchMovie(550)
//        }
    }

    private fun bindUI() = launch {
        val movie = viewModel.movie.await()
        movie.observe(this@HomeFragment, Observer {
            if(it == null) return@Observer

            home_tv.text = it.toString()
        })
    }
}
