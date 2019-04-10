package com.arctouch.codechallenge.detail

import android.databinding.ObservableField
import android.util.Log
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val api: TmdbApi, private val movieImageUrlBuilder: MovieImageUrlBuilder) : BaseViewModel() {

    val backdrop = ObservableField<String>()
    val poster = ObservableField<String>()
    val title = ObservableField<String>()
    val releaseDate = ObservableField<String>()
    val overview = ObservableField<String>()
    val genres = ObservableField<String>()

    fun init(movieId: Int) {

        disposable = api
                .movie(movieId.toLong(), TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.v(DetailViewModel::class.java.simpleName, "movie = $it")
                    prepareData(it)
                }
    }

    private fun prepareData(movie: Movie) {
        title.set(movie.title)
        overview.set(movie.overview)
//        releaseDate.set(formatDate(movie.releaseDate))
        genres.set(buildGenres(movie.genres))

        backdrop.set(movie.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
        poster.set(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
    }

    private fun formatDate(date: String?): String {
        return date?.let {
            //"2019-04-24"
            val formatBackend = SimpleDateFormat("yyyy/MM/dd")
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            sdf.format(formatBackend.parse(it))
        } ?: run {
            ""
        }
    }

    private fun buildGenres(genres: List<Genre>?): String {
        return genres?.let {
            val genre = StringBuilder()
            it.forEach {
                genre.append("${it.name} - ")
            }
            genre.toString()

        } ?: run {
            ""
        }
    }
}