package com.jedrula.moviegeek.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jedrula.moviegeek.R
import com.jedrula.moviegeek.data.network.ConnectivityInterceptorImpl
import com.jedrula.moviegeek.data.network.MovieNetworkDataSourceImpl
import com.jedrula.moviegeek.data.network.TmdbApiService
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
            // TODO: Use the ViewModel

        val tmdbApiService = TmdbApiService(ConnectivityInterceptorImpl(this.context!!))
        val movieDataSource = MovieNetworkDataSourceImpl(tmdbApiService)

        movieDataSource.downloadedMovie.observe(this, Observer {
            home_tv.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
            movieDataSource.fetchMovie(550)
        }
    }

}
