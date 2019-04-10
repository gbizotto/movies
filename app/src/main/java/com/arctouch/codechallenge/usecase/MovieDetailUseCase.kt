package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.extensions.getLanguageApiFormat
import com.arctouch.codechallenge.model.Movie
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MovieDetailUseCase @Inject constructor(private val api: TmdbApi, @Named("apikey") private val apiKey: String, private val locale: Locale) {

    fun getMovieDetails(movieId: Int): Observable<Movie> {
        return api
                .movie(movieId.toLong(), apiKey, locale.getLanguageApiFormat())
    }
}