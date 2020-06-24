package com.faltenreich.release.domain.navigation

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

data class FabConfig(
    val icon: Drawable,
    @ColorInt val backgroundColor: Int,
    @ColorInt val foregroundColor: Int,
    val onClick: () -> Unit
)