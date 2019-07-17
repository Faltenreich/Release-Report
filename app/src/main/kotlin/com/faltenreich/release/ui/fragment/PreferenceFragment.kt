package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.faltenreich.release.R
import com.faltenreich.release.extension.className
import com.faltenreich.release.versionName
import kotlinx.android.synthetic.main.fragment_preference.*

class PreferenceFragment : BaseFragment(R.layout.fragment_preference) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }

        val transaction = fragmentManager?.beginTransaction() ?: return
        val fragment = PreferenceListFragment()
        transaction.replace(R.id.container, fragment, fragment.className)
        transaction.commit()
    }

    // FIXME: Apply inverse theme
    // Attention: Must be public to be properly recreated from instance state
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
}