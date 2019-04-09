package com.arctouch.codechallenge.extensions

import io.reactivex.disposables.Disposable

fun Disposable?.disposeIfNotDisposed() {
    this?.let {
        if (!it.isDisposed) {
            it.dispose()
        }
    }
}

