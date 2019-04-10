package com.arctouch.codechallenge.home

import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.usecase.GenresUseCase
import com.arctouch.codechallenge.usecase.UpcomingMoviesUseCase
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val genresUseCase: GenresUseCase,
        private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
        val movieImageUrlBuilder: MovieImageUrlBuilder
) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    init {
        searchGenres()
    }

    private fun searchGenres() {
        if (Cache.genres.isNullOrEmpty()) {
            disposable = genresUseCase
                    .listGenres()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Cache.cacheGenres(it.genres)
                        searchMovies()
                    }
        } else {
            searchMovies()
        }
    }

    private fun searchMovies() {
        disposable = upcomingMoviesUseCase
                .listUpcomingMovies()
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

