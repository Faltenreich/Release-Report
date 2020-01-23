package com.faltenreich.release.domain.media

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrueOrNull
import com.faltenreich.release.domain.release.detail.ImageListAdapter
import com.faltenreich.release.framework.android.decoration.GridLayoutSpacingItemDecoration
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : BaseFragment(R.layout.fragment_image_list) {

    private val viewModel by lazy { createSharedViewModel(ImageListViewModel::class) }

    private lateinit var imageListAdapter: ImageListAdapter

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
        imageListAdapter = ImageListAdapter(requireContext(), ::openGallery)
    }

    private fun initLayout() {
        val context = context ?: return
        imageListView.layoutManager = GridLayoutManager(context, 2)
        imageListView.addItemDecoration(GridLayoutSpacingItemDecoration(context))
        imageListView.adapter = imageListAdapter
    }

    private fun fetchData() {
        viewModel.observeImageUrls(this, ::setImages)
    }

    private fun setImages(imageUrls: List<String>?) {
        imageListAdapter.removeListItems()
        imageListAdapter.addListItems(imageUrls ?: listOf())
        imageListAdapter.notifyDataSetChanged()

        emptyView.isVisible = imageUrls?.isEmpty().isTrueOrNull
    }

    private fun openGallery(imageUrl: String) {
        val imageUrls = viewModel.imageUrls?.toTypedArray() ?: arrayOf()
        findNavController().navigate(ImageGalleryFragmentDirections.openGallery(imageUrls, imageUrl))
    }
}