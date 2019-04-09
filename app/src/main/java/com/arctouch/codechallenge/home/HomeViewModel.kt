package com.arctouch.codechallenge.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.extensions.disposeIfNotDisposed
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel
//@Inject constructor()
@Inject constructor(api: TmdbApi)
    : ViewModel() {

    private var disposable: Disposable? = null

    val movies = MutableLiveData<List<Movie>>()

    init {
        disposable = api
                .upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    movies.value = moviesWithGenres
                }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.disposeIfNotDisposed()
    }
}

