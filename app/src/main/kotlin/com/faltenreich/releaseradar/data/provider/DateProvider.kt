package com.faltenreich.releaseradar.data.provider

import android.content.Context
import com.faltenreich.releaseradar.extension.asLocalDate
import com.faltenreich.releaseradar.extension.asString
import com.faltenreich.releaseradar.extension.print
import org.threeten.bp.LocalDate

interface DateProvider {
    var releasedAt: String?

    var releaseDate: LocalDate?
        get() = releasedAt?.asLocalDate
        set(value) {
            releasedAt = value?.asString
        }

    fun releaseDateForUi(context: Context?): String? = releaseDate?.print(context)
}