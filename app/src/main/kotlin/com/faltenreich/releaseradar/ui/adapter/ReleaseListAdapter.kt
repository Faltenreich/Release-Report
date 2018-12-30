package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.print
import com.faltenreich.releaseradar.isTrue
import com.faltenreich.releaseradar.ui.viewholder.ReleaseViewHolder

class ReleaseListAdapter(context: Context) : PagedListAdapter<Release, ReleaseViewHolder>(context, EntityDiffUtilItemCallback()), RecyclerSectionItemDecoration.SectionCallback {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseViewHolder = ReleaseViewHolder(context, parent)

    override fun isSection(position: Int): Boolean = position == 0 || itemCount == 0 || getItem(position - 1)?.releaseDate?.isBefore(getItem(position)?.releaseDate).isTrue()

    override fun getSectionHeader(position: Int): CharSequence = takeIf { itemCount > 0  }?.let { getItem(position)?.releaseDate?.print() } ?: ""
}