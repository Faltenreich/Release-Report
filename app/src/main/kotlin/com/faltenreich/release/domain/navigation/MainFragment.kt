package com.faltenreich.release.domain.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.fragment.BaseFragment
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
        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.gallery) {
                bottomAppBar.performHide()
            } else {
                bottomAppBar.performShow()
            }
        }
        bottomAppBar.setNavigationOnClickListener { openNavigation() }
    }

    private fun openNavigation() {
        val arguments = navigationController.currentDestination?.id?.let { id ->
            Bundle().apply { putInt(NavigationFragment.ARGUMENT_PREVIOUS_DESTINATION_ID, id) }
        }
        navigationController.navigate(R.id.navigation, arguments)
    }
}