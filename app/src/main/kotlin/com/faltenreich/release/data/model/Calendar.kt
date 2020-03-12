package com.faltenreich.release.data.model

import com.faltenreich.release.base.date.asLocalDate
import com.faltenreich.release.base.date.asString
import com.parse.ParseObject
import org.threeten.bp.LocalDate

data class Calendar(
    var dateAsString: String? = null,
    var releaseId: String? = null
) : Model {

    override var id: String?
        get() = dateAsString
        set(value) {
            dateAsString = value
        }

    var date: LocalDate?
        get() = dateAsString?.asLocalDate
        set(value) {
            dateAsString = value?.asString
        }

    override fun fromParseObject(parseObject: ParseObject) {
        dateAsString = parseObject.getString(DATE)
        releaseId = parseObject.getString(RELEASE_ID)
    }

    companion object {
        const val DATE = "date"
        const val RELEASE_ID = "releaseId"
    }
}