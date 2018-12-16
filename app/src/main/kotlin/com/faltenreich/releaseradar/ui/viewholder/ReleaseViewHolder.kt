package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.ui.fragment.ReleaseListFragmentDirections
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release, parent) {
    override fun onBind(data: Release) {
        release_name.text = data.artistName?.let { artist -> "$artist - ${data.name}" } ?: data.name
        data.imageUrlForCover?.let { imageUrl -> release_cover.setImageAsync(imageUrl) } ?: release_cover.setImageResource(android.R.color.transparent)
        data.mediaType?.let { mediaType ->
            release_type.setImageResource(mediaType.iconResId)
        }
        release_container.setOnClickListener { openRelease(data) }
    }

    private fun openRelease(release: Release) {
        release.id?.let { id -> Navigation.findNavController(itemView).navigate(ReleaseListFragmentDirections.openRelease(id)) }
    }
}