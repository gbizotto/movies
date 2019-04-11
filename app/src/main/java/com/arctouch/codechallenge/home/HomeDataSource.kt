package com.arctouch.codechallenge.home

import android.arch.paging.PageKeyedDataSource
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.usecase.UpcomingMoviesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeDataSource constructor(
        private val upcomingMoviesUseCase: UpcomingMoviesUseCase,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        compositeDisposable.add(
                searchMovies(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            callback.onResult(mapMovies(it), null, 2)
                        }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
                searchMovies(params.key)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            callback.onResult(mapMovies(it), params.key + 1)
                        }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    private fun searchMovies(page: Int): Observable<UpcomingMoviesResponse> {
        return upcomingMoviesUseCase
                .listUpcomingMovies(page)
    }


    private fun mapMovies(movieResponse: UpcomingMoviesResponse): List<Movie> {
        return movieResponse.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
        }
    }
}