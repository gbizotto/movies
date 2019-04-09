package com.arctouch.codechallenge.di.component

import android.content.Context
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.di.ViewModelFactory
import com.arctouch.codechallenge.di.module.AppModule
import com.arctouch.codechallenge.home.HomeActivity
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

        fun build(): AppComponent
    }

    fun inject(moviesApplication: MoviesApplication)
//    fun inject(activity: HomeActivity)


    fun injectHomeViewModel(): ViewModelFactory<HomeViewModel>
}