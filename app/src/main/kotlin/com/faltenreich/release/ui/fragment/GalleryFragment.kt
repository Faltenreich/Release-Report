package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.faltenreich.release.R
import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.viewmodel.GalleryViewModel
import com.veinhorn.scrollgalleryview.ScrollGalleryView
import com.veinhorn.scrollgalleryview.builder.GallerySettings
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_main.*
import ogbe.ozioma.com.glideimageloader.dsl.DSL.image
import ogbe.ozioma.com.glideimageloader.dsl.DSL.video
import java.lang.IllegalArgumentException

class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {
    private val viewModel by lazy { createViewModel(GalleryViewModel::class) }
    private val releaseId: String? by lazy { arguments?.let { arguments -> GalleryFragmentArgs.fromBundle(arguments).releaseId } }
    private val mediaId: String? by lazy { arguments?.let { arguments -> GalleryFragmentArgs.fromBundle(arguments).mediaId } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initData() {
        releaseId?.let { releaseId ->
            viewModel.observeRelease(releaseId, this) { release ->
                release?.let {
                    viewModel.observeMedia(release, this) { media ->
                        setData(media ?: listOf())
                    }
                }
            }
        }
    }

    private fun setData(mediaList: List<Media>) {
        ScrollGalleryView
            .from(galleryView)
            .settings(GallerySettings
                .from(fragmentManager)
                .thumbnailSize(300)
                .enableZoom(true)
                .build()
            )
            .apply {
                mediaList.forEach { media ->
                    media.url?.let { url ->
                        when (media.mediaType) {
                            MediaType.IMAGE -> add(image(url))
                            MediaType.VIDEO -> add(video(url, R.drawable.transparent))
                            else -> throw IllegalArgumentException("Unsupported media type: ${media.mediaType}")
                        }
                    }

                }
            }
            // Workaround: Fixes https://github.com/VEINHORN/ScrollGalleryView/issues/82
            .onPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
                override fun onPageSelected(position: Int) = Unit
                override fun onPageScrollStateChanged(state: Int) = Unit
            })
            .build()
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "media"
    }
}