package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.extension.tintResource
import com.faltenreich.release.ui.list.item.SpotlightItem
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_spotlight.*
import org.jetbrains.anko.imageResource

class SpotlightViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightItem>(context, R.layout.list_item_spotlight, parent), ReleaseOpener {
    override fun onBind(data: SpotlightItem) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release, spotlightCover) }

        spotlightHeader.text = data.print(context)
        spotlightDate.text = release.releaseDateForUi(context)

        spotlightTitle.text = release.title
        spotlightSubtitle.text = release.subtitle

        spotlightDescription.text = release.description ?: context.getString(R.string.unknown_description)
        spotlightDescription.setTypeface(spotlightDescription.typeface, if (release.description != null) Typeface.NORMAL else Typeface.ITALIC)
        spotlightGenres.isVisible = false // TODO

        release.imageUrlForCover?.let { imageUrl ->
            spotlightCover.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: spotlightCover.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        release.imageUrlForWallpaper?.let { imageUrl ->
            spotlightWallpaper.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: spotlightWallpaper.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        spotlightType.imageResource = release.releaseType?.iconResId ?: android.R.color.transparent
        spotlightType.tintResource = release.releaseType?.colorResId ?: R.color.colorPrimary
        spotlightFavorite.isVisible = release.isFavorite
    }
}