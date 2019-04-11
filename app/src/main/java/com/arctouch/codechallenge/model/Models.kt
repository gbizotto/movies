package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String)

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)

data class Movie(
        val id: Int,
        val title: String,
        val overview: String? = null,
        val genres: List<Genre>? = null,
        @field:Json(name = "genre_ids") val genreIds: List<Int>? = null,
        @field:Json(name = "poster_path") val posterPath: String? = null,
        @field:Json(name = "backdrop_path") val backdropPath: String? = null,
        @field:Json(name = "release_date") val releaseDate: String? = null
)
