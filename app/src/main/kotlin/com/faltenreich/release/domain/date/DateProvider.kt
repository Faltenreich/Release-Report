package com.faltenreich.release.domain.date

import android.content.Context
import com.faltenreich.release.base.date.print
import com.faltenreich.release.data.provider.LabelProvider
import org.threeten.bp.LocalDate

interface DateProvider : LabelProvider {
    val date: LocalDate
    override fun print(context: Context): String? = date.print(context)
    override fun equals(other:Any?): Boolean
}