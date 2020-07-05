package com.faltenreich.release.domain.release.discover

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.setCover
import com.faltenreich.release.framework.android.view.backgroundTint
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_image.*

class DiscoverViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_image, parent),
    ReleaseOpener {

    init {
        container.setOnClickListener { openRelease() }
    }

    override fun onBind(data: ReleaseProvider) {
        val release = data.release

        coverImageView.setCover(release)

        typeImageView.setImageResource(release.releaseType?.iconResId ?: android.R.color.transparent)
        typeImageView.backgroundTint = ContextCompat.getColor(
            context,
            release.releaseType?.colorResId ?: android.R.color.transparent
        )

        subscriptionImageView.visibility = if (release.isSubscribed) View.VISIBLE else View.GONE
    }

    private fun openRelease() {
        openRelease(navigationController, data.release, coverImageView)
    }
}