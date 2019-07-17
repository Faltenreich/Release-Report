package com.faltenreich.release.ui.list.provider

import org.threeten.bp.LocalDate

interface DateProvider {
    val date: LocalDate
    override fun equals(other:Any?): Boolean
}