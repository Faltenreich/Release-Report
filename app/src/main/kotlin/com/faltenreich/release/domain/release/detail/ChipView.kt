package com.faltenreich.release.domain.release.detail

import android.content.Context
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
        setTextColor(ContextCompat.getColor(context, R.color.light))
        setChipBackgroundColorResource(R.color.colorPrimary)
        setChipCornerRadiusResource(R.dimen.card_corner_radius)
        setChipIconTintResource(R.color.light)
        setIconStartPaddingResource(R.dimen.margin_padding_size_xxsmall)
    }
}