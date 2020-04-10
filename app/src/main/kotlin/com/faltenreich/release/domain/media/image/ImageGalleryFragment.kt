package com.faltenreich.release.domain.media.image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.toolbar.fitSystemWindows
import com.faltenreich.release.framework.android.view.viewpager.emptyOnPageChangeListener
import com.faltenreich.release.framework.scrollgalleryview.currentItemWithThumbnail
import com.veinhorn.scrollgalleryview.MediaInfo
import com.veinhorn.scrollgalleryview.ScrollGalleryView
import kotlinx.android.synthetic.main.fragment_image_gallery.*

class ImageGalleryFragment : BaseFragment(R.layout.fragment_image_gallery) {

    private val viewModel by viewModels<ImageGalleryViewModel>()

    private val imageUrls by lazy { ImageGalleryFragmentArgs.fromBundle(requireArguments()).imageUrls }
    private val imageUrl by lazy { ImageGalleryFragmentArgs.fromBundle(requireArguments()).imageUrl }

    private lateinit var scrollGalleryView: ScrollGalleryView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.fitSystemWindows()

        scrollGalleryView = ScrollGalleryView
            .from(galleryView)
            .onPageChangeListener(emptyOnPageChangeListener())
            .settings(viewModel.createGallerySettings(childFragmentManager))
            .build()
    }

    private fun fetchData() {
        viewModel.observeMedia(imageUrls, this, ::setMedia)
    }

    private fun setMedia(media: List<MediaInfo>?) {
        scrollGalleryView.addMedia(media)
        selectImageUrl(imageUrl)
    }

    private fun selectImageUrl(imageUrl: String) {
        scrollGalleryView.currentItemWithThumbnail = imageUrls.indexOf(imageUrl)
    }
}