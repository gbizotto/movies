package com.arctouch.codechallenge.di.module

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
}