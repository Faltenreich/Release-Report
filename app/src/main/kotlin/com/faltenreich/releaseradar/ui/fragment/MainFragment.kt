package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.MainViewModel
import com.faltenreich.releaseradar.extension.fadeBackgroundColorResource
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.activity.BaseActivity
import com.faltenreich.releaseradar.ui.view.TintAction
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val viewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private val searchable by lazy { SearchableObserver() }

    private val navigationHostFragment: NavHostFragment
        get() = childFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment

    private val navigationController: NavController
        get() = navigationHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(searchable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        NavigationUI.setupWithNavController(navigationView, navigationController)
        searchable.properties = SearchableProperties(this, searchView)

        searchView.setLogoIcon(R.drawable.ic_search)
        searchView.setOnLogoClickListener { openSearch() }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                val searchQuery = query?.toString()?.nonBlank ?: return true
                openSearch(searchQuery)
                return true
            }
        })
    }

    private fun initData() {
        viewModel.observeTint(this) { tint -> setTint(tint) }
    }

    private fun openSearch(query: String = "") {
        findNavController().navigate(MainFragmentDirections.searchRelease(query))
    }

    private fun setTint(tint: TintAction?) {
        // TODO: Reveal from tint.source
        val colorRes = tint?.color ?: android.R.color.transparent
        collapsingToolbarLayout.setStatusBarScrimResource(colorRes)
        container.fadeBackgroundColorResource(colorRes)
    }
}