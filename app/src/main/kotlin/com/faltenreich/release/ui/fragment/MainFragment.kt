package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.faltenreich.release.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main, R.menu.main) {
    private val navigationHostFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appCompatActivity?.setSupportActionBar(bottomAppBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> { openSearch(); true }
            R.id.filter -> { openNavigation(); true }
            // TODO: Filter and date picker
            else -> false
        }
    }

    private fun openNavigation() {
        navigationController.navigate(R.id.navigation)
    }

    private fun openSearch() {
        navigationController.navigate(R.id.release_search)
    }
}