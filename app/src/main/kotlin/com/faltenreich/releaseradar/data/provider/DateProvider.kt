package com.faltenreich.releaseradar.data.provider

import android.content.Context
import com.faltenreich.releaseradar.extension.asLocalDate
import com.faltenreich.releaseradar.extension.asString
import com.faltenreich.releaseradar.extension.print
import com.google.firebase.database.Exclude
import org.threeten.bp.LocalDate

interface DateProvider {
    var releasedAt: String?

    @get:Exclude
    var releaseDate: LocalDate?
        get() = releasedAt?.asLocalDate
        set(value) {
            releasedAt = value?.asString
        }

    fun releaseDateForUi(context: Context?): String? = releaseDate?.print(context)
}