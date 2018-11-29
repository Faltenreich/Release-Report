package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseDetailViewModel
import com.faltenreich.releaseradar.setImageAsync
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail) {

    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val releaseId: String? by lazy { ReleaseDetailFragmentArgs.fromBundle(arguments).releaseId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {

    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release ->
                release?.imageUrlForWallpaper?.let { url ->
                    release_wallpaper.setImageAsync(url)
                }
            }
        }
    }
}