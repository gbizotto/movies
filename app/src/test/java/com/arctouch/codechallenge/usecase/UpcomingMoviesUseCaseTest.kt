package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.util.*

class UpcomingMoviesUseCaseTest {
    private val api = mockk<TmdbApi>(relaxed = true)
    private val mockedApiKey = "key"
    private val locale = mockk<Locale>(relaxed = true)

    private val usecase = UpcomingMoviesUseCase(api, mockedApiKey, locale)

    @Test
    fun mustLoadUpcomingMovies() {
        mockUpcomingMovies()
        mockLocale()

        usecase.listUpcomingMovies()
        verify { api.upcomingMovies(mockedApiKey, "pt-BR", 1, "BR") }
    }

    private fun mockLocale() {
        every { locale.toString() }.returns("pt_BR")
        every { locale.country }.returns("BR")
    }

    private fun mockUpcomingMovies() {
        every { api.upcomingMovies(any(), any(), any(), any()) }.returns(Observable.just(mockUpcomingMoviesResponse()))
    }


    private fun mockUpcomingMoviesResponse(): UpcomingMoviesResponse {
        return UpcomingMoviesResponse(1,
                mockMovies(),
                1,
                1)
    }

    private fun mockMovies(): List<Movie> {
        return listOf(
                Movie(1, "test1", null, emptyList())
        )
    }

    private fun mockGenres() {
        every { api.genres(any(), any()) }.returns(Observable.just(mockGenresResponse()))
    }

    private fun mockGenresResponse(): GenreResponse {
        return GenreResponse(listOf())
    }
}