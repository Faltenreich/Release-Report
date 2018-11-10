package com.faltenreich.releaseradar.data.enum

import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.provider.NameResProvider

enum class MediaType(
        override val key: String,
        override val singularStringRes: Int,
        override val pluralStringRes: Int
) : FirebaseEnum, NameResProvider {
    MOVIE("movie", R.string.movie, R.string.movies),
    MUSIC("music", R.string.music, R.string.music),
    VIDEO_GAME("game", R.string.video_game, R.string.video_games);

    companion object {
        fun valueForKey(key: String): MediaType? = values().firstOrNull { type -> type.key == key }
    }
}