package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.ui.adapter.ReleaseViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        initViewPager()
        initSearchView()
    }

    private fun initSearchView() {
        searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
    }

    private fun initViewPager() {
        val content = MediaType.values().map { mediaType -> getString(mediaType.pluralStringRes) to ReleaseListFragment() }
        val adapter = ReleaseViewPagerAdapter(fragmentManager, content)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}