package com.faltenreich.release.framework.android.view.image

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat

var ImageView.tint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { imageTintList = ColorStateList.valueOf(value) }

var ImageView.tintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { tint = ContextCompat.getColor(context, value) }