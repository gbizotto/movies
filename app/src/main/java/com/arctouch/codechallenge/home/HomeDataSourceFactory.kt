package com.arctouch.codechallenge.home

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.usecase.UpcomingMoviesUseCase
import io.reactivex.disposables.CompositeDisposable

class HomeDataSourceFactory constructor(
        private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
        private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {
    val moviesDataSourceLiveData = MutableLiveData<HomeDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val homeDataSource = HomeDataSource(upcomingMoviesUseCase, compositeDisposable)
        moviesDataSourceLiveData.postValue(homeDataSource)
        return homeDataSource
    }
}