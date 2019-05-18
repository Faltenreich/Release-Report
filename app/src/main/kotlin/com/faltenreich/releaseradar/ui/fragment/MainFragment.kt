package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.extension.nonBlank
import com.lapism.searchview.Search
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
        NavigationUI.setupWithNavController(navigationView, navigationController)

        searchView.setLogoIcon(R.drawable.ic_search)
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                val searchQuery = query?.toString()?.nonBlank ?: return true
                searchView.logo = Search.Logo.ARROW
                findNavController().navigate(MainFragmentDirections.searchRelease(searchQuery))
                return true
            }
        })
    }
}