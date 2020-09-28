package com.faltenreich.release.framework.android.view.fab

import android.content.res.ColorStateList
import com.google.android.material.floatingactionbutton.FloatingActionButton

var FloatingActionButton.backgroundTint: Int?
    get() = backgroundTintList?.defaultColor
    set(value) { backgroundTintList = value?.let { ColorStateList.valueOf(value) } }

var FloatingActionButton.foregroundTint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) = setColorFilter(value)