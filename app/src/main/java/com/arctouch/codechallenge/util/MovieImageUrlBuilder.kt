package com.arctouch.codechallenge.util

import javax.inject.Inject
import javax.inject.Named

class MovieImageUrlBuilder @Inject constructor(@Named("apikey") private val apiKey: String) {
    private val posterUrl = "https://image.tmdb.org/t/p/w154"
    private val backdropUrl = "https://image.tmdb.org/t/p/w780"

    fun buildPosterUrl(posterPath: String): String {
        return "$posterUrl$posterPath?api_key=$apiKey"
    }

    fun buildBackdropUrl(backdropPath: String): String {
        return "$backdropUrl$backdropPath?api_key=$apiKey"
    }
}
