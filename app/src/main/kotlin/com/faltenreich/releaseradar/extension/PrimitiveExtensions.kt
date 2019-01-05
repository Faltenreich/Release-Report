package com.faltenreich.releaseradar.extension

val Any.className: String
    get() = javaClass.simpleName

fun Boolean?.isTrue() = this ?: false

fun Boolean?.isTrueOrNull() = this ?: true

fun Boolean?.isFalse() = this?.let { !it } ?: false

fun Boolean?.isFalseOrNull() = this?.let { !it } ?: true