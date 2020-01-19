package com.faltenreich.release.domain.media

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.fitSystemWindows
import com.veinhorn.scrollgalleryview.ScrollGalleryView
import com.veinhorn.scrollgalleryview.builder.GallerySettings
import kotlinx.android.synthetic.main.fragment_image_gallery.*
import ogbe.ozioma.com.glideimageloader.dsl.DSL

class ImageGalleryFragment : BaseFragment(R.layout.fragment_image_gallery) {

    private val imageUrls by lazy { ImageGalleryFragmentArgs.fromBundle(requireArguments()).imageUrls }

    private lateinit var scrollGalleryView: ScrollGalleryView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.fitSystemWindows()

        val settings = GallerySettings
            .from(childFragmentManager)
            .thumbnailSize(THUMBNAIL_SIZE)
            .enableZoom(true)
            .build()

        scrollGalleryView = ScrollGalleryView
            .from(galleryView)
            .onPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
                override fun onPageSelected(position: Int) = Unit
            })
            .settings(settings)
            .apply {
                imageUrls.forEach { imageUrl -> add(DSL.image(imageUrl)) }
            }
            .build()
    }

    companion object {
        private const val THUMBNAIL_SIZE = 300
    }
}