package com.faltenreich.releaseradar

val Any.tag: String
    get() = this::class.java.simpleName