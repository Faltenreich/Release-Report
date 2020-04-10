package com.faltenreich.release.base.date

import android.content.Context
import android.util.Log
import com.faltenreich.release.R
import com.faltenreich.release.base.log.tag
import com.faltenreich.release.domain.preference.UserPreferences
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.WeekFields
import java.util.*

private const val FORMAT_TIME = "HH:mm"
private const val FORMAT_DATE = "yyyy-MM-dd"

val LocalDate.isToday: Boolean
    get() = isEqual(LocalDate.now())

fun LocalDate.isAfterOrEqual(date: LocalDate): Boolean {
    return !isBefore(date)
}

fun LocalDate.isBeforeOrEqual(date: LocalDate): Boolean {
    return !isAfter(date)
}

val String.asLocalDate: LocalDate?
    get() = try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(FORMAT_DATE))
    } catch (exception: DateTimeParseException) {
        Log.e(tag, exception.message ?: "Failed to parse String to LocalDate")
        null
    }

val LocalDate.asString: String
    get() = format(DateTimeFormatter.ofPattern(FORMAT_DATE))

val LocalDate.date: Date
    get() = DateTimeUtils.toDate(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

val Date.localDate: LocalDate
    get() = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

val LocalTime.asString: String
    get() = format(DateTimeFormatter.ofPattern(FORMAT_TIME))

fun LocalTime.print(): String {
    return asString
}

val String.asLocalTime: LocalTime?
    get() = try {
        LocalTime.parse(this, DateTimeFormatter.ofPattern(FORMAT_TIME))
    } catch (exception: DateTimeParseException) {
        Log.e(tag, exception.message)
        null
    }

fun LocalDate.print(context: Context?): String? {
    val today = LocalDate.now()
    return when (ChronoUnit.DAYS.between(today, this)) {
        0L -> context?.getString(R.string.today)
        -1L -> context?.getString(R.string.yesterday)
        1L -> context?.getString(R.string.tomorrow)
        else -> {
            val weekDay = dayOfWeek.getDisplayName(TextStyle.FULL, UserPreferences.locale)
            // TODO: Do not print year for current year
            val dateString = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(this)
            "$weekDay, $dateString"
        }
    }
}

fun YearMonth.print(): String? {
    val year = year
    val isSameYear = year == LocalDate.now().year
    val monthText = month.getDisplayName(TextStyle.FULL, UserPreferences.locale)
    return if (isSameYear) monthText else "$monthText $year"
}

val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

val LocalDate.atStartOfWeek: LocalDate
    get() = with(WeekFields.of(UserPreferences.locale).dayOfWeek(), 1)

val LocalDate.atEndOfWeek: LocalDate
    get() = with(WeekFields.of(UserPreferences.locale).dayOfWeek(), 7)