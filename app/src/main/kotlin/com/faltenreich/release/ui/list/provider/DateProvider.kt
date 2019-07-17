package com.faltenreich.release.ui.list.provider

import android.content.Context
import com.faltenreich.release.extension.print
import org.threeten.bp.LocalDate

interface DateProvider : LabelProvider {
    val date: LocalDate
    override fun print(context: Context): String? = date.print(context)
    override fun equals(other:Any?): Boolean
}