package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.extension.fadeBackgroundColorResource
import com.faltenreich.release.extension.nonBlank
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.view.TintAction
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main), PopupMenu.OnMenuItemClickListener {
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.settings -> { openSettings(); true }
            else -> false
        }
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
        searchView.setOnOpenCloseListener(object : Search.OnOpenCloseListener {
            override fun onOpen() {
                if (isAdded) {
                    moreButton.isVisible = false
                }
            }
            override fun onClose() {
                if (isAdded) {
                    moreButton.isVisible = true
                }
            }
        })
        moreButton.setOnClickListener { openMenu() }
    }

    private fun initData() {
        viewModel.observeTint(this) { tint -> setTint(tint) }
    }

    private fun openSearch(query: String = "") {
        findNavController().navigate(MainFragmentDirections.searchRelease(query))
    }

    private fun openMenu() {
        val popup = PopupMenu(requireContext(), moreButton)
        popup.inflate(R.menu.main)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    private fun openSettings() {

    }

    private fun setTint(tint: TintAction?) {
        // TODO: Reveal from tint.source
        val colorRes = tint?.color ?: android.R.color.transparent
        collapsingToolbarLayout.setStatusBarScrimResource(colorRes)
        container.fadeBackgroundColorResource(colorRes)
    }
}