package com.faltenreich.releaseradar.data

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import java.util.*

val LocalDate.date: Date
    get() = DateTimeUtils.toDate(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

val Date.localDate: LocalDate
    get() = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.print(): String = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun LocalDate.printMonth(): String = month.getDisplayName(TextStyle.FULL, Locale.getDefault())

fun LocalDate.printYear(): String = year.toString()