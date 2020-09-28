package com.faltenreich.release.domain.release.list

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.setThumbnail
import com.faltenreich.release.framework.android.animation.ColorAnimation
import com.faltenreich.release.framework.android.animation.provider.ImageViewTintProvider
import com.faltenreich.release.framework.android.context.getColorFromAttribute
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

    init {
        container.setOnClickListener { openRelease() }
        subscriptionImageView.setOnClickListener { toggleSubscription() }
    }

    override fun onBind(data: ReleaseProvider) {
        val release = data.release

        releaseDateTextView.text = release.releaseDateForUi(context)
        coverImageView.setThumbnail(release)
        titleTextView.text = release.title
        descriptionTextView.text = release.artistIfRelevant ?: release.description

        invalidateSubscription(release)
    }

    private fun invalidateSubscription(release: Release) {
        subscriptionImageView.setImageResource(
            if (release.isSubscribed) R.drawable.ic_subscription_on
            else R.drawable.ic_subscription_off)
        val foregroundColor =
            if (release.isSubscribed) ContextCompat.getColor(context, R.color.yellow)
            else context.getColorFromAttribute(android.R.attr.textColorSecondary)
        ColorAnimation(ImageViewTintProvider(subscriptionImageView), foregroundColor).start()
    }

    private fun openRelease() {
        openRelease(navigationController, data.release, coverImageView)
    }

    private fun toggleSubscription() {
        val release = data.release
        val isSubscribed = !(release.isSubscribed)
        release.isSubscribed = isSubscribed
        invalidateSubscription(release)
    }
}