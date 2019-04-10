package com.arctouch.codechallenge.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.usecase.GenresUseCase
import com.arctouch.codechallenge.usecase.UpcomingMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class HomeViewModelTest {

    private val genreUseCase = mockk<GenresUseCase>(relaxed = true)
    private val upcomingMoviesUseCase = mockk<UpcomingMoviesUseCase>(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val immediateScheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    @Before
    fun setUp() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }

        mockGenres()
        mockUpcomingMovies()
        viewModel = HomeViewModel(genreUseCase, upcomingMoviesUseCase)
    }

    @Test
    fun onInit_mustLoadUpcomingMovies() {
        verify { genreUseCase.listGenres() }
        verify { upcomingMoviesUseCase.listUpcomingMovies() }

        Assert.assertEquals(mockMovies(), viewModel.movies.value)
    }

    private fun mockUpcomingMovies() {
        every { upcomingMoviesUseCase.listUpcomingMovies() }.returns(Observable.just(mockUpcomingMoviesResponse()))
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
        every { genreUseCase.listGenres() }.returns(Observable.just(mockGenresResponse()))
    }

    private fun mockGenresResponse(): GenreResponse {
        return GenreResponse(listOf())
    }

}