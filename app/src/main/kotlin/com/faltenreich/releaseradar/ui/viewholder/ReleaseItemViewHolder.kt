package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import com.faltenreich.releaseradar.ui.fragment.ReleaseListFragmentDirections
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseItemViewHolder(context: Context, parent: ViewGroup) : ReleaseViewHolder(context, R.layout.list_item_release, parent) {

    override fun onBind(data: ReleaseListItem) {
        data.release?.let { release ->
            cardView.setOnClickListener { openRelease(release) }
            release.imageUrlForCover?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl) } ?: releaseCoverImageView.setImageResource(android.R.color.transparent)
            releaseTypeImageView.setImageResource(release.mediaType?.iconResId ?: android.R.color.transparent)
            releaseTypeImageView.background = ContextCompat.getDrawable(context, R.drawable.dogear)?.apply { setTint(ContextCompat.getColor(context, release.mediaType?.colorResId ?: R.color.colorPrimary)) }
            releaseNameTextView.text = release.artistName?.let { artist -> "$artist - ${release.name}" } ?: release.name
        }
    }

    private fun openRelease(release: Release) {
        release.id?.let { id -> Navigation.findNavController(itemView).navigate(ReleaseListFragmentDirections.openRelease(id)) }
    }
}