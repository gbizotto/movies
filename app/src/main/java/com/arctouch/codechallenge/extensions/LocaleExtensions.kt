package com.arctouch.codechallenge.extensions

import java.util.*

fun Locale.getLanguageApiFormat(): String {
    return this.toString().replace("_", "-")
}