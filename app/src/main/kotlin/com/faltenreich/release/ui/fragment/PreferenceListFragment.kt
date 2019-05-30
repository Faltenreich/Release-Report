package com.faltenreich.release.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.faltenreich.release.R

class PreferenceListFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}