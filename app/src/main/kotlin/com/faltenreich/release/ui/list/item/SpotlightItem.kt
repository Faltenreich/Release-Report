package com.faltenreich.release.ui.list.item

import android.content.Context
import androidx.annotation.StringRes
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.logic.provider.LabelProvider
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

data class SpotlightItem(
    override val release: Release,
    @StringRes val headerResId: Int
) : ReleaseProvider, LabelProvider {
    override fun print(context: Context): String? = context.getString(headerResId)
}