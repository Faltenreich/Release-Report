package com.faltenreich.release.domain.preference

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.faltenreich.release.R
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.primitive.className
import com.faltenreich.release.framework.android.context.versionCode
import com.faltenreich.release.framework.android.context.versionName
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_preference.*

class PreferenceFragment : BaseFragment(R.layout.fragment_preference) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }

        val transaction = fragmentManager?.beginTransaction() ?: return
        val fragment =
            PreferenceListFragment()
        transaction.replace(R.id.container, fragment, fragment.className)
        transaction.commit()
    }

    // Attention: Must be public to be properly recreated from instance state
    class PreferenceListFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            UserPreferences.registerOnSharedPreferenceChangeListener(this)
            invalidateSummaries()
        }

        override fun onPause() {
            super.onPause()
            UserPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            invalidateSummaries()
        }

        private fun invalidateSummaries() {
            if (isAdded) {
                findPreference<Preference>(getString(R.string.preference_version_name))?.summary =
                    context?.versionName

                findPreference<Preference>(getString(R.string.preference_version_code))?.summary =
                    context?.versionCode?.toString()

                findPreference<Preference>(getString(R.string.preference_reminder_time))?.summary =
                    context?.getString(R.string.reminder_time_desc, UserPreferences.reminderTime.print())
            }
        }
    }
}