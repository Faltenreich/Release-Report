package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.viewpager.ReleaseViewPagerAdapter
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_spotlight_viewpager.*

class SpotlightViewPagerFragment : BaseFragment(R.layout.fragment_spotlight_viewpager) {
    private val searchable by lazy { SearchableObserver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(searchable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    override fun onResume() {
        super.onResume()
        searchView.logo = Search.Logo.HAMBURGER_ARROW
    }

    private fun initLayout() {
        searchable.properties = SearchableProperties(this, searchView, appbarLayout, statusBarBackground)
        initViewPager()
        initSearch()
    }

    private fun initSearch() {
        searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                query?.toString()?.nonBlank?.let {
                    searchView.logo = Search.Logo.ARROW
                    findNavController().navigate(ReleaseListFragmentDirections.searchRelease(it))
                }
                return true
            }
        })
    }

    private fun initViewPager() {
        val content = MediaType.values().map { mediaType -> getString(mediaType.pluralStringRes) to SpotlightFragment() }
        val adapter = ReleaseViewPagerAdapter(fragmentManager, content)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}