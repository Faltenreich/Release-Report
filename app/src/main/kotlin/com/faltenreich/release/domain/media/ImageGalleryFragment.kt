package com.faltenreich.release.domain.media

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.viewpager.widget.ViewPager
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.fitSystemWindows
import com.faltenreich.release.framework.scrollgalleryview.GlideImageLoader
import com.veinhorn.scrollgalleryview.MediaInfo
import com.veinhorn.scrollgalleryview.ScrollGalleryView
import com.veinhorn.scrollgalleryview.builder.GallerySettings
import kotlinx.android.synthetic.main.fragment_image_gallery.*

class ImageGalleryFragment : BaseFragment(R.layout.fragment_image_gallery) {

    private val imageUrls by lazy { ImageGalleryFragmentArgs.fromBundle(requireArguments()).imageUrls }
    private val imageUrl by lazy { ImageGalleryFragmentArgs.fromBundle(requireArguments()).imageUrl }

    private lateinit var scrollGalleryView: ScrollGalleryView
    private lateinit var thumbnailsContainer: ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        selectImageUrl(imageUrl)
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.fitSystemWindows()

        val settings = GallerySettings
            .from(childFragmentManager)
            .thumbnailSize(THUMBNAIL_SIZE)
            .enableZoom(true)
            .build()
        val media = imageUrls.map { imageUrl -> MediaInfo.mediaLoader(GlideImageLoader(imageUrl)) }

        scrollGalleryView = ScrollGalleryView
            .from(galleryView)
            .onPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
                override fun onPageSelected(position: Int) = Unit
            })
            .settings(settings)
            .add(media)
            .build()
        thumbnailsContainer = scrollGalleryView.findViewById(com.veinhorn.scrollgalleryview.R.id.thumbnails_container)
    }

    private fun selectImageUrl(imageUrl: String) {
        val index = imageUrls.indexOf(imageUrl)
        scrollGalleryView.currentItem = index
        // Workaround: ScrollGalleryView.setCurrentItem() does not scroll to Thumbnail
        thumbnailsContainer.getChildAt(index).doOnPreDraw { thumbnail -> thumbnail.performClick() }
    }

    companion object {
        private const val THUMBNAIL_SIZE = 400
    }
}