package com.faltenreich.release.domain.media.video

fun String.thumbnailUrl(): String? {
    return thumbnailUrlForYouTube()
}

private fun String.thumbnailUrlForYouTube(): String? {
    val delimiter = "?v="
    val startIndexOfId = indexOf(delimiter).takeIf { it >= 0 } ?: return null
    val id = substring(startIndexOfId + delimiter.length)
    return "https://img.youtube.com/vi/$id/0.jpg"
}