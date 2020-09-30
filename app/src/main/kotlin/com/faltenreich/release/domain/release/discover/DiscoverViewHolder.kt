package com.faltenreich.release.domain.release.discover

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.setCover
import com.faltenreich.release.framework.android.animation.ColorAnimation
import com.faltenreich.release.framework.android.animation.provider.ImageViewTintProvider
import com.faltenreich.release.framework.android.animation.provider.ViewBackgroundTintProvider
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_image.*

class DiscoverViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_image, parent),
    ReleaseOpener {

    init {
        container.setOnClickListener { openRelease() }
        subscriptionImageView.setOnClickListener { toggleSubscription() }
    }

    override fun onBind(data: ReleaseProvider) {
        val release = data.release

        titleTextView.isVisible = true
        titleTextView.text = data.release.titleFull

        coverImageView.setCover(release) { cover ->
            titleTextView.isVisible = cover == null
        }

        invalidateSubscription(release)
    }

    private fun invalidateSubscription(release: Release) {
        subscriptionImageView.setImageResource(
            if (release.isSubscribed) R.drawable.ic_subscription_on
            else R.drawable.ic_subscription_off
        )

        val backgroundColor =
            if (release.isSubscribed) ContextCompat.getColor(context, R.color.yellow)
            else context.getColorFromAttribute(R.attr.backgroundColorPrimary)
        ColorAnimation(ViewBackgroundTintProvider(subscriptionImageView), backgroundColor).start()

        val foregroundColor =
            if (release.isSubscribed) ContextCompat.getColor(context, R.color.brown)
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