package com.faltenreich.releaseradar.extension

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields
import java.util.*

private const val DATE_FORMAT_FIREBASE = "yyyy-MM-dd"

val String?.asLocalDate: LocalDate?
    get() = try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT_FIREBASE))
    } catch (exception: DateTimeParseException) {
        println(exception)
        null
    }

val LocalDate.asString: String
    get() = format(DateTimeFormatter.ofPattern(DATE_FORMAT_FIREBASE))

val LocalDate.date: Date
    get() = DateTimeUtils.toDate(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

val Date.localDate: LocalDate
    get() = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.print(): String = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun LocalDate.printMonth(): String = month.getDisplayName(TextStyle.FULL, Locale.getDefault())

fun LocalDate.printYear(): String = year.toString()

val LocalDate.calendarWeek: Int
    get() = get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())