package com.arctouch.codechallenge.home

import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(api: TmdbApi) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    init {
        disposable = api
                .upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mapMovies(it)
                }
    }

    private fun mapMovies(movieResponse: UpcomingMoviesResponse) {
        val moviesWithGenres = movieResponse.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
        }
        movies.value = moviesWithGenres
    }
}

