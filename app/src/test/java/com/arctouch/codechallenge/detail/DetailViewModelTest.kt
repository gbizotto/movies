package com.arctouch.codechallenge.detail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.usecase.MovieDetailUseCase
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
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

class DetailViewModelTest {
    private val useCase = mockk<MovieDetailUseCase>(relaxed = true)
    private val imageBuilder = mockk<MovieImageUrlBuilder>(relaxed = true)

    private lateinit var viewModel: DetailViewModel

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

        mockMovieDetailsApi()
        mockImageBuilder()

        viewModel = DetailViewModel(imageBuilder, useCase)
    }

    @Test
    fun whenMovieIdIsReady_mustSearchForMovieDetails() {
        viewModel.init(1)

        verify { useCase.getMovieDetails(1) }

        Assert.assertEquals("test1", viewModel.title.get())
        Assert.assertEquals("test2", viewModel.overview.get())
        Assert.assertEquals("test3", viewModel.backdrop.get())
        Assert.assertEquals("test4", viewModel.poster.get())
        Assert.assertEquals("", viewModel.genres.get())
    }

    private fun mockMovieDetailsApi() {
        every { useCase.getMovieDetails(any()) }.returns(Observable.just(mockMovieDetails()))
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

    private fun mockImageBuilder() {
        every { imageBuilder.buildBackdropUrl(any()) }.returns("test3")
        every { imageBuilder.buildPosterUrl(any()) }.returns("test4")
    }
}