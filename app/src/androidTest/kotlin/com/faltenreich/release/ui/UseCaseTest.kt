package com.faltenreich.release.ui

import android.content.Context
import androidx.annotation.IdRes
import androidx.navigation.findNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.faltenreich.release.R
import com.faltenreich.release.helper.IdlingResource
import com.faltenreich.release.ui.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class UseCaseTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    protected val activity: MainActivity
        get() = activityRule.activity

    protected val context: Context by lazy { InstrumentationRegistry.getInstrumentation().targetContext }

    private val idlingResource: IdlingResource by lazy { IdlingResource }

    @Before
    fun setup() {
        idlingResource.register()
    }

    @After
    fun cleanup() {
        idlingResource.unregister()
    }

    protected fun openFragment(@IdRes idRes: Int) {
        activity.apply {
            runOnUiThread {
                findNavController(R.id.appNavigationHost).navigate(idRes, null)
            }
        }
    }
}
