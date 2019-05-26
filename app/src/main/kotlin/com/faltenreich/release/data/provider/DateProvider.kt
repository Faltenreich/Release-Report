package com.faltenreich.release.data.provider

import android.content.Context
import com.faltenreich.release.extension.asLocalDate
import com.faltenreich.release.extension.asString
import com.faltenreich.release.extension.print
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