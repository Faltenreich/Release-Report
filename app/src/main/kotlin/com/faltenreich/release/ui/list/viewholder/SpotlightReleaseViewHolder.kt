package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.adapter.SpotlightContainerListAdapter
import com.faltenreich.release.ui.list.decoration.SpotlightReleaseItemDecoration
import com.faltenreich.release.ui.list.item.SpotlightReleaseItem
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_spotlight.*

class SpotlightReleaseViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightReleaseItem>(context, R.layout.list_item_spotlight, parent), ReleaseOpener {
    override fun onBind(data: SpotlightReleaseItem) {

        titleView.text = data.print(context)
        data.releases.firstOrNull()?.release?.imageUrlForWallpaper?.let { imageUrl ->
            imageView.setImageAsync(imageUrl)
        } ?: imageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        listView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        listView.addItemDecoration(SpotlightReleaseItemDecoration(context))
        val listAdapter = SpotlightContainerListAdapter(context)
        listView.adapter = listAdapter
        listAdapter.addListItems(data.releases)
        listAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}