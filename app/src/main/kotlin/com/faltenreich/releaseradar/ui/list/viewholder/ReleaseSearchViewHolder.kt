package com.faltenreich.releaseradar.ui.list.viewholder

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.print
import com.faltenreich.releaseradar.extension.screenSize
import com.faltenreich.releaseradar.extension.setImageAsync
import com.faltenreich.releaseradar.extension.tintResource
import com.faltenreich.releaseradar.ui.fragment.ReleaseDetailFragment
import kotlinx.android.synthetic.main.list_item_release_search.*

class ReleaseSearchViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release_search, parent) {

    override fun onBind(data: Release) {
        container.setOnClickListener { openRelease(data) }
        data.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(android.R.color.transparent)
        releaseTypeImageView.setImageResource(data.mediaType?.iconResId ?: android.R.color.transparent)
        releaseTypeImageView.tintResource = data.mediaType?.colorResId ?: R.color.colorPrimary
        releaseNameTextView.text = data.artistName?.let { artist -> "$artist - ${data.title}" } ?: data.title
        releaseDateTextView.text = data.releaseDateForUi(context)
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