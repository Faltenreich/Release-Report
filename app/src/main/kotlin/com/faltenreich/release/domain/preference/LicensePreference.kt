package com.faltenreich.release.domain.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import com.faltenreich.release.R
import com.mikepenz.aboutlibraries.LibsBuilder

class LicensePreference @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : Preference(context, attributeSet) {

    override fun onClick() {
        super.onClick()
        openLicenses()
    }

    private fun openLicenses() {
        LibsBuilder().apply {
            withFields(R.string::class.java.fields)
            activityTitle = context.getString(R.string.licenses)
            aboutShowIcon = false
            aboutShowVersionName = false
            aboutShowVersionCode = false
        }.start(context)
    }
}