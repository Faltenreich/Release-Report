package com.faltenreich.release.data.enum

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.faltenreich.release.R
import com.faltenreich.release.data.provider.IconProvider
import com.faltenreich.release.data.provider.NameResProvider
import com.faltenreich.release.data.provider.TintProvider

enum class ReleaseType(
    override val key: String,
    override val singularStringRes: Int,
    override val pluralStringRes: Int,
    @ColorRes override val colorResId: Int,
    @DrawableRes override val iconResId: Int
) : StorableEnum, NameResProvider, TintProvider, IconProvider {
    MOVIE(
        "movie",
        R.string.movie,
        R.string.movies,
        R.color.blue,
        R.drawable.ic_movie
    ),
    MUSIC(
        "music",
        R.string.music,
        R.string.music,
        R.color.red,
        R.drawable.ic_music
    ),
    GAME(
        "game",
        R.string.game,
        R.string.games,
        R.color.green,
        R.drawable.ic_game
    );

    companion object {
        fun valueForKey(key: String): ReleaseType? =
            values().firstOrNull { type -> type.key == key }
    }
}