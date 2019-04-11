package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.DaggerComponentProvider
import com.arctouch.codechallenge.di.component.AppComponent
import com.arctouch.codechallenge.di.component.DaggerAppComponent
import com.arctouch.codechallenge.di.module.AppModule

class MoviesApplication : Application(), DaggerComponentProvider {

    override val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .applicationContext(applicationContext)
                .appModule(AppModule(this))
                .build()
    }
}