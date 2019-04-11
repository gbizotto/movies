package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.extensions.getLanguageApiFormat
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class UpcomingMoviesUseCase @Inject constructor(private val api: TmdbApi, @Named("apikey") private val apiKey: String, private val locale: Locale) {

    fun listUpcomingMovies(): Observable<UpcomingMoviesResponse> {
        return api
                .upcomingMovies(apiKey, locale.getLanguageApiFormat(), 1, locale.country)
    }
}