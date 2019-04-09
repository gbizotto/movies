package com.arctouch.codechallenge.detail

import android.util.Log
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val api: TmdbApi) : BaseViewModel() {

    fun init(movieId: Int) {

        disposable = api
                .movie(movieId.toLong(), TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.v(DetailViewModel::class.java.simpleName, "movie = $it")
                }

    }
}