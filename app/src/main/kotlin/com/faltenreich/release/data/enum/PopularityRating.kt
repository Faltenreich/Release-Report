package com.faltenreich.release.data.enum

import androidx.annotation.DrawableRes
import com.faltenreich.release.R

enum class PopularityRating(
    val min: Float,
    @DrawableRes val iconRes: Int
) {
    LOW(0f, R.drawable.ic_popularity_low),
    MEDIUM(40f, R.drawable.ic_popularity_medium),
    HIGH(80f, R.drawable.ic_popularity_high);

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