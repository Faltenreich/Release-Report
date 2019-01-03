package com.faltenreich.releaseradar.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.R
import com.google.android.material.chip.Chip

class Chip @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attributeSet, defStyleAttr) {

    init {
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        setChipBackgroundColorResource(R.color.colorPrimary)
        setChipCornerRadiusResource(R.dimen.card_corner_radius)
        setChipIconTintResource(android.R.color.white)
        setIconStartPaddingResource(R.dimen.margin_padding_size_xsmall)
    }
}