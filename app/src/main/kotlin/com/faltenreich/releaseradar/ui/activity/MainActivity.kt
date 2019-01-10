package com.faltenreich.releaseradar.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.ui.ToolbarDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main), ToolbarDelegate {

    private val navigationHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.navigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.navigationHost).navigateUp()

    override fun onHamburgerIconClicked() = if (drawerLayout.isDrawerOpen(navigationView)) drawerLayout.closeDrawer(navigationView) else drawerLayout.openDrawer(navigationView)

    private fun initLayout() {
        NavigationUI.setupWithNavController(navigationView, navigationController)
        // Workaround: Title is not set on app start via Navigation Architecture Components, so we do it on our own
        title = navigationController.currentDestination?.label
    }
}