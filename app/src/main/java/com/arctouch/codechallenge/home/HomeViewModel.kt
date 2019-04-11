package com.arctouch.codechallenge.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.extensions.disposeIfNotDisposed
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.usecase.GenresUseCase
import com.arctouch.codechallenge.usecase.UpcomingMoviesUseCase
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val genresUseCase: GenresUseCase,
        private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
        val movieImageUrlBuilder: MovieImageUrlBuilder
) : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movies = MutableLiveData<List<Movie>>()
    val movieList = MutableLiveData<PagedList<Movie>>()

    lateinit var newsList: LiveData<PagedList<Movie>>

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
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .setInitialLoadSizeHint(20 * 2)
                .build()
        val factory = HomeDataSourceFactory(upcomingMoviesUseCase, compositeDisposable)
//        factory.create()
         newsList = LivePagedListBuilder<Int, Movie>(factory, config).build()
        movieList.value = LivePagedListBuilder<Int, Movie>(factory, config).build().value


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.disposeIfNotDisposed()
    }
}

