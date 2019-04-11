package com.arctouch.codechallenge.di.component

import android.content.Context
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.detail.DetailViewModel
import com.arctouch.codechallenge.di.ViewModelFactory
import com.arctouch.codechallenge.di.module.AppModule
import com.arctouch.codechallenge.home.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun injectHomeViewModel(): ViewModelFactory<HomeViewModel>
    fun injectDetailViewModel(): ViewModelFactory<DetailViewModel>
}