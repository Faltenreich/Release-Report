package com.faltenreich.releaseradar.data.enum

import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.provider.ColorProvider

enum class Rating(override val colorResId: Int) : ColorProvider {
    HIGH(R.color.green),
    MEDIUM(R.color.yellow),
    LOW(R.color.red);

    companion object {
        fun valueOf(value: Float): Rating = when {
            value >= 7f -> HIGH
            value >= 5f -> MEDIUM
            else -> LOW
        }
    }
}