package com.faltenreich.release.domain.media.video

object VideoThumbnailFactory {

    fun createThumbnail(url: String): String? {
        return when  {
            url.contains("youtube.com") -> createThumbnailForYouTube(url)
            else -> null
        }
    }

    private fun createThumbnailForYouTube(url: String): String? {
        val delimiter = "?v="
        val startIndexOfId = url.indexOf(delimiter).takeIf { it >= 0 } ?: return null
        val id = url.substring(startIndexOfId + delimiter.length)
        return "https://img.youtube.com/vi/$id/0.jpg"
    }
}