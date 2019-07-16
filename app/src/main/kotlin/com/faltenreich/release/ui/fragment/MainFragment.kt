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
        initLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date_select -> { openDatePicker(); true }
            R.id.search -> { openSearch(); true }
            R.id.filter -> { openFilter(); true }
            else -> false
        }
    }

    private fun initLayout() {
        appCompatActivity?.setSupportActionBar(bottomAppBar)
        bottomAppBar.setNavigationOnClickListener { openNavigation() }
    }

    private fun openNavigation() {
        navigationController.navigate(R.id.navigation)
    }

    private fun openDatePicker() {
        navigationController.navigate(R.id.date_select)
    }

    private fun openSearch() {
        navigationController.navigate(R.id.release_search)
    }

    private fun openFilter() {

    }
}