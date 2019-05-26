package com.faltenreich.release.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navigationHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.appNavigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp()
    }

    private fun initLayout() {
        // Workaround: Title is not set on app start via Navigation Architecture Components, so we do it on our own
        title = navigationController.currentDestination?.label
    }
}