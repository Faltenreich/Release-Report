package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.viewmodel.GalleryViewModel
import com.faltenreich.release.ui.viewpager.GalleryPagerAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {
    private val viewModel by lazy { createViewModel(GalleryViewModel::class) }
    private val releaseId: String? by lazy { arguments?.let { arguments -> GalleryFragmentArgs.fromBundle(arguments).releaseId } }
    private val mediaId: String? by lazy { arguments?.let { arguments -> GalleryFragmentArgs.fromBundle(arguments).mediaId } }

    private lateinit var listAdapter: GalleryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        // TODO: ViewCompat.setTransitionName(releaseCoverImageView, SHARED_ELEMENT_NAME)

        listAdapter = GalleryPagerAdapter()
        viewPager.adapter = listAdapter
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

    private fun setData(media: List<Media>) {
        listAdapter.removeListItems()
        listAdapter.addListItems(media)
        listAdapter.notifyDataSetChanged()
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "media"
    }
}