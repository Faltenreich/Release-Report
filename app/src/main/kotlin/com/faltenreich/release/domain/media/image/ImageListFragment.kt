package com.faltenreich.release.domain.media.image

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrueOrNull
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.recyclerview.decoration.GridLayoutSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : BaseFragment(R.layout.fragment_image_list), ImageOpener {

    private val viewModel by activityViewModels<ImageListViewModel>()

    private lateinit var listAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun init() {
        listAdapter = ImageListAdapter(
            requireContext(),
            ::openGallery
        )
    }

    private fun initLayout() {
        val context = context ?: return
        imageListView.layoutManager = GridLayoutManager(context, 2)
        imageListView.addItemDecoration(GridLayoutSpacingItemDecoration(context))
        imageListView.adapter = listAdapter
    }

    private fun fetchData() {
        viewModel.observeImageUrls(this, ::setImages)
    }

    private fun setImages(imageUrls: List<String>?) {
        listAdapter.removeListItems()
        listAdapter.addListItems(imageUrls ?: listOf())
        listAdapter.notifyDataSetChanged()

        emptyView.isVisible = imageUrls?.isEmpty().isTrueOrNull
    }

    private fun openGallery(imageUrl: String) {
        val imageUrls = viewModel.imageUrls?.toTypedArray() ?: arrayOf()
        openImage(findNavController(), imageUrls, imageUrl)
    }
}