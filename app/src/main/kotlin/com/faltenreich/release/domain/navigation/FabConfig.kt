package com.faltenreich.release.domain.navigation

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class FabConfig(
    @DrawableRes val iconRes: Int,
    @ColorRes val backgroundColorRes: Int,
    @ColorRes val foregroundColorRes: Int,
    val onClick: () -> Unit
)