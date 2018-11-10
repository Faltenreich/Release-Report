package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.releaseradar.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        searchView.setOnLogoClickListener { toolbarInteractive?.onHamburgerIconClicked() }
    }
}