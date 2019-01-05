package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.screenSize
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import com.faltenreich.releaseradar.ui.fragment.ReleaseDetailFragment
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseItemViewHolder(context: Context, parent: ViewGroup) : ReleaseViewHolder(context, R.layout.list_item_release, parent) {

    override fun onBind(data: ReleaseListItem) {
        data.release?.let { release ->
            cardView.setOnClickListener { openRelease(release) }
            release.imageUrlForCover?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(android.R.color.transparent)
            releaseTypeImageView.setImageResource(release.mediaType?.iconResId ?: android.R.color.transparent)
            releaseTypeImageView.background = ContextCompat.getDrawable(context, R.drawable.dogear)?.apply { setTint(ContextCompat.getColor(context, release.mediaType?.colorResId ?: R.color.colorPrimary)) }
            releaseNameTextView.text = release.artistName?.let { artist -> "$artist - ${release.title}" } ?: release.title
        }
    }

    private fun openRelease(release: Release) {
        release.id?.let { id ->
            ViewCompat.setTransitionName(releaseCoverImageView, ReleaseDetailFragment.SHARED_ELEMENT_NAME)
            itemView.findNavController().navigate(
                R.id.open_release,
                Bundle().apply { putString("releaseId", id) },
                null,
                FragmentNavigatorExtras(releaseCoverImageView to ReleaseDetailFragment.SHARED_ELEMENT_NAME)
            )
        }
    }
}