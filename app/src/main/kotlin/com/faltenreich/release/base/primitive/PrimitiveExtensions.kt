package com.faltenreich.release.base.primitive

val Any.className: String
    get() = javaClass.simpleName

val Boolean?.isTrue
    get() = this ?: false

val Boolean?.isTrueOrNull
    get() = this ?: true

val Boolean?.isFalse
    get() = this?.let { !it } ?: false

val Boolean?.isFalseOrNull
    get()  = this?.let { !it } ?: true

val String?.nonBlank: String?
    get() = this?.takeIf(String::isNotBlank)

fun <T, U> Pair<T?, U?>.takeIfNotEmpty(): Pair<T, U>? {
    return if (first != null && second != null) {
        first!! to second!!
    } else {
        null
    }
}