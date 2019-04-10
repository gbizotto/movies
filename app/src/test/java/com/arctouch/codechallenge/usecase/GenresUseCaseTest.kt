package com.arctouch.codechallenge.usecase

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.GenreResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.util.*

class GenresUseCaseTest {

    private val api = mockk<TmdbApi>(relaxed = true)
    private val mockedApiKey = "key"
    private val locale = mockk<Locale>(relaxed = true)

    private val usecase = GenresUseCase(api, mockedApiKey, locale)

    @Test
    fun mustLoadUGenres() {
        mockGenres()
        mockLocale()

        usecase.listGenres()
        verify { api.genres(mockedApiKey, "pt-BR") }
    }

    private fun mockLocale() {
        every { locale.toString() }.returns("pt_BR")
        every { locale.country }.returns("BR")
    }

    private fun mockGenres() {
        every { api.genres(any(), any()) }.returns(Observable.just(mockGenresResponse()))
    }

    private fun mockGenresResponse(): GenreResponse {
        return GenreResponse(listOf())
    }
}