package com.faltenreich.release.domain.media

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ImageListAdapter
import com.faltenreich.release.framework.android.decoration.GridLayoutSpacingItemDecoration
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : BaseFragment(R.layout.fragment_image_list), ImageUrlObserver {

    private lateinit var imageListAdapter: ImageListAdapter

    override var imageUrls: List<String>? = null
        set(value) {
            field = value
            if (isAdded) {
                setImages()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        setImages()
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

    private fun setImages() {
        val imageUrls = imageUrls ?: listOf()

        imageListAdapter.removeListItems()
        imageListAdapter.addListItems(imageUrls)
        imageListAdapter.notifyDataSetChanged()

        emptyView.isVisible = imageUrls.isEmpty()
    }

    private fun openGallery(imageUrl: String) {
        val imageUrls = imageUrls?.toTypedArray() ?: arrayOf()
        findNavController().navigate(ImageGalleryFragmentDirections.openGallery(imageUrls, imageUrl))
    }
}