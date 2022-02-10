package com.faltenreich.release.domain.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.faltenreich.release.R
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.primitive.className
import com.faltenreich.release.domain.reminder.Reminder
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

        private val preferenceForVersionName: Preference?
            get() = findPreference(R.string.preference_version_name)

        private val preferenceForVersionCode: Preference?
            get() = findPreference(R.string.preference_version_code)

        private val preferenceForReminderTime: Preference?
            get() = findPreference(R.string.preference_reminder_time)

        private val preferenceForWeeklyReminderIsEnabled: Preference?
            get() = findPreference(R.string.preference_reminder_weekly_is_enabled)

        private val preferenceForSubscriptioRemindersIsEnabled: Preference?
            get() = findPreference(R.string.preference_reminder_subscriptions_is_enabled)

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
            invalidatePreferences()
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
        }

        private fun findPreference(@StringRes stringRes: Int): Preference? {
            return findPreference(getString(stringRes))
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            val context = context ?: return
            invalidatePreferences()
            when (key) {
                context.getString(R.string.preference_reminder_weekly_is_enabled) -> refreshReminder(context)
                context.getString(R.string.preference_reminder_subscriptions_is_enabled) -> refreshReminder(context)
            }
        }

        private fun refreshReminder(context: Context) {
            Reminder.refresh(context, replace = true)
        }

        private fun invalidatePreferences() {
            if (isAdded) {
                preferenceForVersionName?.summary = context?.versionName
                preferenceForVersionCode?.summary = context?.versionCode?.toString()

                preferenceForReminderTime?.let { preference ->
                    preference.summary = context?.getString(
                        R.string.reminder_time_desc,
                        UserPreferences.reminderTime.print()
                    )
                }
            }
        }
    }
}