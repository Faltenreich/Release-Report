package com.faltenreich.release.domain.media.video

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrueOrNull
import com.faltenreich.release.framework.android.decoration.LinearLayoutSpacingItemDecoration
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_list.*

class VideoListFragment : BaseFragment(R.layout.fragment_video_list) {

    private val viewModel by lazy { createSharedViewModel(VideoListViewModel::class) }

    private lateinit var listAdapter: VideoListAdapter

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
        listAdapter = VideoListAdapter(requireContext())
    }

    private fun initLayout() {
        val context = context ?: return
        videoListView.layoutManager = LinearLayoutManager(context)
        videoListView.addItemDecoration(LinearLayoutSpacingItemDecoration(context))
        videoListView.adapter = listAdapter
    }

    private fun fetchData() {
        viewModel.observeVideoUrls(this, ::setVideos)
    }

    private fun setVideos(videoUrls: List<String>?) {
        listAdapter.removeListItems()
        listAdapter.addListItems(videoUrls ?: listOf())
        listAdapter.notifyDataSetChanged()

        emptyView.isVisible = videoUrls?.isEmpty().isTrueOrNull
    }
}