package com.faltenreich.release.domain.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import com.faltenreich.release.R
import de.psdev.licensesdialog.LicensesDialog

class LicensePreference @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : Preference(context, attributeSet) {

    override fun onClick() {
        super.onClick()
        LicensesDialog.Builder(context)
            .setNotices(R.raw.licenses)
            .setTitle(R.string.licenses)
            .setIncludeOwnLicense(true)
            .build()
            .show()
    }
}