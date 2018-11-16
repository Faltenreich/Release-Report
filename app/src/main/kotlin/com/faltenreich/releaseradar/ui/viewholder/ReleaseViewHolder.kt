package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.tint
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release, parent) {
    override fun onBind(data: Release) {
        release_name.text = context.getString(R.string.release_title, data.artistName, data.name)
        release_description.text = data.description
        data.imageUrl?.let { imageUrl ->
            release_cover.setImageAsync(imageUrl)
        }
        data.mediaType?.let { mediaType ->
            release_type.setImageResource(mediaType.iconResId)
            release_type.tint = ContextCompat.getColor(context, mediaType.colorResId)
        }
        release_container.setOnClickListener { openRelease(data) }
    }

    private fun openRelease(release: Release) {
        // TODO: Open ReleaseDetailFragment via itemView.navigationController
    }
}