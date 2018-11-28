package com.faltenreich.releaseradar.data

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import java.util.*

private const val DATE_FORMAT_FIREBASE = "yyyy-MM-dd"

val String?.asLocalDate: LocalDate?
    get() = try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT_FIREBASE))
    } catch (exception: DateTimeParseException) {
        println(exception)
        null
    }

val LocalDate.asString: String?
    get() = try {
        format(DateTimeFormatter.ofPattern(DATE_FORMAT_FIREBASE))
    } catch (exception: DateTimeException) {
        println(exception)
        null
    }

val LocalDate.date: Date
    get() = DateTimeUtils.toDate(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

val Date.localDate: LocalDate
    get() = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.print(): String = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)

fun LocalDate.printMonth(): String = month.getDisplayName(TextStyle.FULL, Locale.getDefault())

fun LocalDate.printYear(): String = year.toString()