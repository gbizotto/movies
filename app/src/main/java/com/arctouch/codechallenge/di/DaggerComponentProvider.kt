package com.arctouch.codechallenge.di

import android.app.Activity
import com.arctouch.codechallenge.di.component.AppComponent

interface DaggerComponentProvider {

    val component: AppComponent
}

val Activity.injector get() = (application as DaggerComponentProvider).component
