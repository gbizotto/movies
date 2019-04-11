package com.arctouch.codechallenge.detail

import android.databinding.ObservableField
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.extensions.formatGenres
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.usecase.MovieDetailUseCase
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieImageUrlBuilder: MovieImageUrlBuilder, private val movieDetailUseCase: MovieDetailUseCase) : BaseViewModel() {

    val backdrop = ObservableField<String>()
    val poster = ObservableField<String>()
    val title = ObservableField<String>()
    val releaseDate = ObservableField<String>()
    val overview = ObservableField<String>()
    val genres = ObservableField<String>()

    fun init(movieId: Int) {

        disposable = movieDetailUseCase
                .getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    prepareData(it)
                }
    }

    private fun prepareData(movie: Movie) {
        title.set(movie.title)
        overview.set(movie.overview)
//        releaseDate.set(formatDate(movie.releaseDate))
        releaseDate.set(movie.releaseDate)
        genres.set(movie.formatGenres())
        backdrop.set(movie.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
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
}