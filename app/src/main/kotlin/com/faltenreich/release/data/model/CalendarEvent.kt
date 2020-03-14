package com.faltenreich.release.data.model

import com.faltenreich.release.base.date.localDate
import com.parse.ParseObject
import org.threeten.bp.LocalDate

data class CalendarEvent(
    var date: LocalDate? = null,
    var imageUrl: String? = null
) : Model {

    override var id: String?
        get() = throw UnsupportedOperationException()
        set(value) {
            throw UnsupportedOperationException()
        }

    override fun fromParseObject(parseObject: ParseObject) {
        date = parseObject.getDate(DATE)?.localDate
        imageUrl = parseObject.getString(IMAGE_URL)
    }

    companion object {
        const val DATE = "date"
        const val IMAGE_URL = "imageUrl"

        fun fromRelease(release: Release): CalendarEvent {
            return CalendarEvent().apply {
                date = release.releaseDate
                imageUrl = release.imageUrlForThumbnail
            }
        }
    }
}