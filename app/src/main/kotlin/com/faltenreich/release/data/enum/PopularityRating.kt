package com.faltenreich.release.data.enum

import androidx.annotation.DrawableRes
import com.faltenreich.release.R

enum class PopularityRating(
    val min: Float,
    @DrawableRes val iconRes: Int = R.drawable.ic_popularity
) {
    LOW(0f),
    MEDIUM(40f),
    HIGH(80f);

    companion object {

        fun ofPopularity(popularity: Float): PopularityRating {
            return when {
                popularity >= HIGH.min -> HIGH
                popularity >= MEDIUM.min -> MEDIUM
                else -> LOW
            }
        }
    }
}