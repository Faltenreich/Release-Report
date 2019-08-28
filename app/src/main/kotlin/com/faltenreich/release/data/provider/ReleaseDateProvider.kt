package com.faltenreich.release.data.provider

import android.content.Context
import com.faltenreich.release.base.date.asLocalDate
import com.faltenreich.release.base.date.asString
import com.faltenreich.release.base.date.print
import org.threeten.bp.LocalDate

interface ReleaseDateProvider {
    var releasedAt: String?

    var releaseDate: LocalDate?
        get() = releasedAt?.asLocalDate
        set(value) {
            releasedAt = value?.asString
        }

    fun releaseDateForUi(context: Context?): String? = releaseDate?.print(context)
}