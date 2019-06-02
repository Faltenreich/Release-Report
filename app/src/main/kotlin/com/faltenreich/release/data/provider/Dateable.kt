package com.faltenreich.release.data.provider

import org.threeten.bp.LocalDate

interface Dateable {
    val date: LocalDate?
}