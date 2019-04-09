package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.RxImmediateSchedulerRule
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @Rule
    @JvmField
    var schedulers = RxImmediateSchedulerRule()


    private val api = mockk<TmdbApi>(relaxed = true)

    @Before
    fun setup() {
        mockUpcomingMovies()
    }

    @Test
    fun onInit_mustLoadUpcomingMovies() {
        val viewModel = HomeViewModel(api)
        verify { api.upcomingMovies(any(), any(), any(), any()) }
    }

    private fun mockUpcomingMovies() {
        every { api.upcomingMovies(any(), any(), any(), any()) }.returns(Observable.just(mockUpcomingMoviesResponse()))
    }

    private fun mockUpcomingMoviesResponse(): UpcomingMoviesResponse {
        return UpcomingMoviesResponse(1,
                listOf<Movie>(),
                1,
                1)
    }

}