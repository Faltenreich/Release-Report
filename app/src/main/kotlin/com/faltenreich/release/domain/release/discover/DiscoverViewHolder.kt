package com.faltenreich.release.domain.release.discover

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.view.backgroundTint
import com.faltenreich.release.framework.android.view.setCover
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_image.*
import org.jetbrains.anko.imageResource

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

        typeImageView.imageResource = release.releaseType?.iconResId ?: android.R.color.transparent
        typeImageView.backgroundTint = ContextCompat.getColor(context, release.releaseType?.colorResId ?: R.color.colorPrimary)

        subscriptionImageView.visibility = if (release.isSubscribed) View.VISIBLE else View.GONE

        titleTextView.text = release.title

        artistTextView.text = release.artist
        artistTextView.isVisible = artistTextView.text.isNotBlank()
    }

    private fun openRelease() {
        openRelease(navigationController, data.release, coverImageView)
    }
}