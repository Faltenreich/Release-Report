package com.faltenreich.release.data.enum

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.context.getColorFromAttribute

enum class PopularityRating(
    val min: Float,
    @DrawableRes val iconRes: Int = R.drawable.ic_popularity
) {
    LOW(0f),
    MEDIUM(40f),
    HIGH(80f);

    @ColorInt
    fun getColor(context: Context): Int {
        return when (this) {
            LOW -> context.getColorFromAttribute(android.R.attr.textColorPrimary)
            MEDIUM -> ContextCompat.getColor(context, R.color.yellow)
            HIGH -> ContextCompat.getColor(context, R.color.red)
        }
    }

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