package com.faltenreich.releaseradar.data.enum

import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.provider.ColorProvider
import com.faltenreich.releaseradar.data.provider.IconProvider
import com.faltenreich.releaseradar.data.provider.NameResProvider
import com.faltenreich.releaseradar.firebase.database.model.FirebaseEnum

enum class MediaType(
        override val key: String,
        override val singularStringRes: Int,
        override val pluralStringRes: Int,
        override val colorResId: Int,
        override val colorDarkResId: Int,
        override val iconResId: Int
) : FirebaseEnum, NameResProvider, ColorProvider, IconProvider {
    MOVIE("movie", R.string.movie, R.string.movies, R.color.blue, R.color.blue_dark, R.drawable.ic_movie),
    MUSIC("music", R.string.music, R.string.music, R.color.red, R.color.red_dark, R.drawable.ic_music),
    GAME("game", R.string.game, R.string.games, R.color.green, R.color.green_dark, R.drawable.ic_game);

    companion object {
        fun valueForKey(key: String): MediaType? = values().firstOrNull { type -> type.key == key }
    }
}