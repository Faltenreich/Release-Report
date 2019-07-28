package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.adapter.SpotlightContainerListAdapter
import com.faltenreich.release.ui.list.decoration.DividerItemDecoration
import com.faltenreich.release.ui.list.item.SpotlightReleaseItem
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_spotlight.*

class SpotlightReleaseViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightReleaseItem>(context, R.layout.list_item_spotlight, parent), ReleaseOpener {
    private val listAdapter = SpotlightContainerListAdapter(context)

    init {
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(DividerItemDecoration(context))
        listView.adapter = listAdapter
    }

    override fun onBind(data: SpotlightReleaseItem) {
        titleView.text = data.print(context)
        data.releases.firstOrNull()?.release?.imageUrlForWallpaper?.let { imageUrl ->
            imageView.setImageAsync(imageUrl)
        } ?: imageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        listAdapter.removeListItems()
        listAdapter.addListItems(data.releases)
        listAdapter.notifyDataSetChanged()
    }
}