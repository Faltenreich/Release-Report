package com.faltenreich.release.framework.android.animation.provider

import android.content.Context

interface ColorProvider {
    var color: Int?
    val context: Context
    val shouldAnimate: Boolean
}