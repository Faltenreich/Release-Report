package com.faltenreich.release.domain.release.detail

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.google.android.material.chip.Chip

class ChipView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attributeSet, defStyleAttr) {
    init {
        val foregroundColor = ContextCompat.getColor(context, android.R.color.white)
        setTextColor(foregroundColor)
        setChipBackgroundColorResource(R.color.colorPrimary)
        setChipCornerRadiusResource(R.dimen.card_corner_radius)
        chipIconTint = ColorStateList.valueOf(foregroundColor)
        setIconStartPaddingResource(R.dimen.margin_padding_size_xxsmall)
        setEnsureMinTouchTargetSize(false)
    }
}