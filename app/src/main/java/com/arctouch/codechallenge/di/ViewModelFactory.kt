package com.arctouch.codechallenge.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/*
Source: https://proandroiddev.com/dagger-2-on-android-the-simple-way-f706a2c597e9
 */
class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val viewModel: Provider<VM>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModel.get() as T
}
