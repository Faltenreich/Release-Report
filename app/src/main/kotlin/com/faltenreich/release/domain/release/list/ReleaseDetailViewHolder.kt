package com.faltenreich.release.domain.release.list

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.setThumbnail
import com.faltenreich.release.framework.android.view.image.tintResource
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_detail.*

class ReleaseDetailViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<ReleaseProvider>(
    context,
    R.layout.list_item_release_detail,
    parent
), ReleaseOpener {

    override fun onBind(data: ReleaseProvider) {
        val release = data.release
        container.setOnClickListener {
            openRelease(navigationController, release, coverImageView)
        }

        releaseDateTextView.text = release.releaseDateForUi(context)

        coverImageView.setThumbnail(release)

        typeImageView.setImageResource(release.releaseType?.iconResId ?: android.R.color.transparent)
        typeImageView.tintResource = release.releaseType?.colorResId ?: android.R.color.transparent

        titleTextView.text = release.title
        descriptionTextView.text = release.artistIfRelevant ?: release.description

        subscriptionImageView.isVisible = release.isSubscribed
    }
}