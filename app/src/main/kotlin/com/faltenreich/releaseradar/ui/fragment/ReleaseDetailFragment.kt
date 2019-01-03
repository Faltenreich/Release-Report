package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.print
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
        context?.let { context ->
            toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }
    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release ->
                toolbar.title = release?.name
                releaseDescriptionTextView.text = release?.description
                releaseDateTextView.text = release?.releaseDate?.print()
                release?.imageUrlForWallpaper?.let { url -> releaseWallpaperImageView.setImageAsync(url) } ?: releaseWallpaperImageView.setImageResource(android.R.color.transparent)
                release?.mediaType?.let { mediaType -> collapsingToolbarLayout.setContentScrimResource(mediaType.colorResId) }
            }
        }
    }
}