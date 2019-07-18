package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val navigationHostFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        appCompatActivity?.setSupportActionBar(bottomAppBar)
        navigationController.addOnDestinationChangedListener { _, _, _ -> bottomAppBar.performShow() }
        bottomAppBar.setNavigationOnClickListener { openNavigation() }
    }

    private fun openNavigation() {
        val arguments = navigationController.currentDestination?.id?.let { id -> Bundle().apply { putInt("previousDestinationId", id) } }
        navigationController.navigate(R.id.navigation, arguments)
    }
}