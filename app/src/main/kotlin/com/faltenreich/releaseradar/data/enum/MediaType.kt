package com.faltenreich.releaseradar.data.enum

import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.provider.ColorProvider
import com.faltenreich.releaseradar.data.provider.IconProvider
import com.faltenreich.releaseradar.data.provider.NameResProvider

enum class MediaType(
        override val key: String,
        override val singularStringRes: Int,
        override val pluralStringRes: Int,
        override val colorResId: Int,
        override val iconResId: Int
) : FirebaseEnum, NameResProvider, ColorProvider, IconProvider {
    MOVIE("movie", R.string.movie, R.string.movies, R.color.blue, R.drawable.ic_movie),
    MUSIC("music", R.string.music, R.string.music, R.color.red, R.drawable.ic_music),
    VIDEO_GAME("game", R.string.video_game, R.string.video_games, R.color.green, R.drawable.ic_videogame);

    companion object {
        fun valueForKey(key: String): MediaType? = values().firstOrNull { type -> type.key == key }
    }
}