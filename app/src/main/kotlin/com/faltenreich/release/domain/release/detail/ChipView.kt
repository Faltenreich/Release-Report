package com.faltenreich.release.domain.release.detail

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.google.android.material.chip.Chip

class ChipView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attributeSet, defStyleAttr) {
    init {
        val foregroundColor = context.getColorFromAttribute(android.R.attr.textColorPrimary)
        setTextColor(foregroundColor)
        chipBackgroundColor = ColorStateList.valueOf(context.getColorFromAttribute(R.attr.backgroundColorSecondary))
        setChipCornerRadiusResource(R.dimen.card_corner_radius)
        chipIconTint = ColorStateList.valueOf(foregroundColor)
        setIconStartPaddingResource(R.dimen.margin_padding_size_xxsmall)
        setEnsureMinTouchTargetSize(false)
    }
}