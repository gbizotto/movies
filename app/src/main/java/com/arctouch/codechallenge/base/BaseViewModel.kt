package com.arctouch.codechallenge.base

import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.extensions.disposeIfNotDisposed
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable.disposeIfNotDisposed()
    }
}