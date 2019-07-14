package com.faltenreich.release.ui.list.item

import org.threeten.bp.LocalDate

interface DateItem {
    val date: LocalDate
    override fun equals(other:Any?): Boolean
}