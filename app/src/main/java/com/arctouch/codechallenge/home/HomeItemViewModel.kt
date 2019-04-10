package com.arctouch.codechallenge.home

import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.extensions.formatGenres
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder

class HomeItemViewModel(private val movie: Movie, pathBuilder: MovieImageUrlBuilder, private val clickEvent: (id: Int) -> Unit) : ViewModel() {
    val title = movie.title
    val genres = movie.formatGenres()
    val releaseDate = movie.releaseDate
    val poster = movie.posterPath?.let { pathBuilder.buildPosterUrl(movie.posterPath) }

    fun onSelected() {
        clickEvent(movie.id)
    }
}