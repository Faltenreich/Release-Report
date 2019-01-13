package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.extension.fadeBackgroundColorResource
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.viewpager.FragmentViewPagerAdapter
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
        val types = MediaType.values()
        val content = types.map { type -> getString(type.pluralStringRes) to SpotlightFragment.newInstance(type) }
        val adapter = FragmentViewPagerAdapter(fragmentManager, content)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                setTint(types[position])
            }
        })
        setTint(types[0], false)
    }

    private fun setTint(type: MediaType, animated: Boolean = true) {
        if (animated) {
            statusBarBackground.fadeBackgroundColorResource(type.colorResId)
            appbarLayout.fadeBackgroundColorResource(type.colorResId)
            viewPager.fadeBackgroundColorResource(type.colorDarkResId)
        } else {
            statusBarBackground.setBackgroundResource(type.colorResId)
            appbarLayout.setBackgroundResource(type.colorResId)
            viewPager.setBackgroundResource(type.colorDarkResId)
        }
    }
}