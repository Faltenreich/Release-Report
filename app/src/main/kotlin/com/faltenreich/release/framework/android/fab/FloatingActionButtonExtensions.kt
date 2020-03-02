package com.faltenreich.release.framework.android.fab

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

var FloatingActionButton.backgroundTint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { backgroundTintList = ColorStateList.valueOf(value) }

var FloatingActionButton.backgroundTintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { backgroundTint = ContextCompat.getColor(context, value) }

var FloatingActionButton.foregroundTint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) = setColorFilter(value)

var FloatingActionButton.foregroundTintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { foregroundTint = ContextCompat.getColor(context, value) }