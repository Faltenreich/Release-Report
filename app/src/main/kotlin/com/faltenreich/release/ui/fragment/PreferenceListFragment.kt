package com.faltenreich.release.ui.fragment

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.faltenreich.release.R
import com.faltenreich.release.versionName

class PreferenceListFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        invalidateSummaries()
    }

    private fun invalidateSummaries() {
        if (isAdded) {
            findPreference<Preference>(getString(R.string.preference_version))?.summary = context?.versionName
        }
    }
}