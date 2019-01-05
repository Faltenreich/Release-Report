package com.faltenreich.releaseradar.data.provider

import com.faltenreich.releaseradar.extension.asLocalDate
import com.faltenreich.releaseradar.extension.asString
import com.google.firebase.database.Exclude
import org.threeten.bp.LocalDate

interface DateProvider {
    var releasedAt: String?

    @get:Exclude
    var releaseDate: LocalDate?
        get() = releasedAt?.asLocalDate
        set(value) { releasedAt = value?.asString }
}