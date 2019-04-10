package com.arctouch.codechallenge.extensions

import com.arctouch.codechallenge.model.Movie

fun Movie.formatGenres(): String? {
    return this.genres?.joinToString(separator = ", ") { it.name }
}