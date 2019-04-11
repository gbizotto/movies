package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.extensions.getLanguageApiFormat
import com.arctouch.codechallenge.model.GenreResponse
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class GenresUseCase @Inject constructor(private val api: TmdbApi, @Named("apikey") private val apiKey: String, private val locale: Locale) {

    fun listGenres(): Observable<GenreResponse> {
        return api.genres(apiKey, locale.getLanguageApiFormat())
    }
}