package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.ui.fragment.ReleaseListFragmentDirections
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release, parent) {

    override fun onBind(data: Release) {
        cardView.setOnClickListener { openRelease(data) }
        data.imageUrlForCover?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl) } ?: releaseCoverImageView.setImageResource(android.R.color.transparent)
        releaseTypeImageView.setImageResource(data.mediaType?.iconResId ?: android.R.color.transparent)
        releaseTypeImageView.background = ContextCompat.getDrawable(context, R.drawable.dogear)?.apply { setTint(ContextCompat.getColor(context, data.mediaType?.colorResId ?: R.color.colorPrimary)) }
        releaseNameTextView.text = data.artistName?.let { artist -> "$artist - ${data.name}" } ?: data.name
    }

    private fun openRelease(release: Release) {
        release.id?.let { id -> Navigation.findNavController(itemView).navigate(ReleaseListFragmentDirections.openRelease(id)) }
    }
}