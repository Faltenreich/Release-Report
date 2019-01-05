package com.faltenreich.releaseradar.data.provider

import com.faltenreich.releaseradar.extension.asLocalDate
import com.faltenreich.releaseradar.extension.asString
import com.google.firebase.database.Exclude
import org.threeten.bp.LocalDate

interface DateProvider {
    var releasedAtString: String?

    @get:Exclude
    var releaseDate: LocalDate?
        get() = releasedAtString?.asLocalDate
        set(value) { releasedAtString = value?.asString }
}