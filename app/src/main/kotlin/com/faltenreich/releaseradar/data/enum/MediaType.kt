package com.faltenreich.releaseradar.data.enum

enum class MediaType(override val key: String) : FirebaseEnum {
    MOVIE("movie"),
    MUSIC("music"),
    VIDEO_GAME("game");

    companion object {
        fun valueForKey(key: String): MediaType? = values().firstOrNull { type -> type.key == key }
    }
}