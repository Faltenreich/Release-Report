package com.faltenreich.release.domain.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference

class LicensePreference @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : Preference(context, attributeSet) {

    override fun onClick() {
        super.onClick()
        // TODO
    }
}