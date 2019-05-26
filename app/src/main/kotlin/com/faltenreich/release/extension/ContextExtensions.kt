package com.faltenreich.release.extension

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import java.util.*

val Context.screenSize: Point
    get() = Point().also { point ->
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(point)
    }

val Context.locale: Locale
    get() = resources.configuration.locale