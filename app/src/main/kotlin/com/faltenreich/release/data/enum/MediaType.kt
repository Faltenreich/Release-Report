package com.faltenreich.release.data.enum

enum class MediaType(
    override val key: String
) : StorableEnum {
    IMAGE("image"),
    VIDEO("video");

    companion object {
        fun valueForKey(key: String): MediaType? = values().firstOrNull { type -> type.key == key }
    }
}