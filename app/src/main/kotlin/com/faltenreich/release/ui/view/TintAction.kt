package com.faltenreich.release.ui.view

import android.graphics.PointF
import androidx.annotation.ColorRes

data class TintAction(
    @ColorRes val color: Int,
    val source: PointF? = null
)