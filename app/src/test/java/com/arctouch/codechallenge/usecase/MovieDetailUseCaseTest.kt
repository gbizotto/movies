package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.util.*

class MovieDetailUseCaseTest {
    private val api = mockk<TmdbApi>(relaxed = true)
    private val mockedApiKey = "test"

    private val locale = mockk<Locale>(relaxed = true)

    private val usecase = MovieDetailUseCase(api, mockedApiKey, locale)

    @Test
    fun mustLoadUMovieDetails() {
        mockMovieDetailsApi()
        mockLocale()

        usecase.getMovieDetails(1)
        verify { api.movie(1, mockedApiKey, "pt-BR") }
    }

    private fun mockLocale() {
        every { locale.toString() }.returns("pt_BR")
        every { locale.country }.returns("BR")
    }

    private fun mockMovieDetailsApi() {
        every { api.movie(any(), any(), any()) }.returns(Observable.just(mockMovieDetails()))
    }

    private fun mockMovieDetails(): Movie {
        return Movie(
                id = 1,
                title = "test1",
                overview = "test2",
                genres = emptyList(),
                backdropPath = "test3",
                posterPath = "test4",
                releaseDate = "test5"
        )
    }

}