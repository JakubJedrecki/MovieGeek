package com.jedrula.moviegeek.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jedrula.moviegeek.data.db.entity.Movie
import com.jedrula.moviegeek.data.network.interfaces.ConnectivityInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY = ""
const val BASE_URL = "https://api.themoviedb.org/3/"
//https://api.themoviedb.org/3/movie/550?api_key=

interface TmdbApiService {

    @GET("movie/{movieId}")
    fun getMovie(
        @Path("movieId") movieId: Int
    ): Deferred<Movie>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TmdbApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbApiService::class.java)
        }
    }
}