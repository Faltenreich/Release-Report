package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.ui.adapter.ReleaseViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight) {

    override fun onResume() {
        super.onResume()
        invalidatePaddingForTranslucentStatusBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun invalidatePaddingForTranslucentStatusBar() {
        view?.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            appbarLayout.setPadding(0, frame.top, 0, 0)
            searchView.setPadding(0, frame.top, 0, 0)
            statusBarBackground.layoutParams.height = frame.top
        }
    }

    private fun initLayout() {
        initViewPager()
        initSearchView()
    }

    private fun initSearchView() {
        searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
    }

    private fun initViewPager() {
        val content = MediaType.values().map { mediaType -> getString(mediaType.pluralStringRes) to SpotlightCategoryFragment() }
        val adapter = ReleaseViewPagerAdapter(fragmentManager, content)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}