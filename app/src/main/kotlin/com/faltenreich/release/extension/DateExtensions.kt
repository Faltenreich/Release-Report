package com.faltenreich.release.extension

import android.content.Context
import com.faltenreich.release.R
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.WeekFields
import java.util.*

private const val DATE_FORMAT_FIREBASE = "yyyy-MM-dd"

val LocalDate.isToday: Boolean
    get() = isEqual(LocalDate.now())

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

val LocalDate.calendarWeek: Int
    get() = get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

val LocalDateTime.millis: Long
    get() = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

val LocalDate.millis: Long
    get() = atTime(0, 0).millis

fun LocalDate.print(context: Context?): String? {
    val today = LocalDate.now()
    val daysBetween = ChronoUnit.DAYS.between(today, this)
    return when (daysBetween) {
        0L -> context?.getString(R.string.today)
        -1L -> context?.getString(R.string.yesterday)
        1L -> context?.getString(R.string.tomorrow)
        else -> DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)
    }
}

fun YearMonth.print(context: Context?): String? {
    val year = year
    val isSameYear = year == LocalDate.now().year
    val monthText = month.getDisplayName(TextStyle.FULL, context?.locale)
    return if (isSameYear) monthText else "$monthText $year"
}

val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

val LocalDate.atStartOfMonth: LocalDate
    get() = withDayOfMonth(1)

val LocalDate.atEndOfMonth: LocalDate
    get() = withDayOfMonth(lengthOfMonth())

fun LocalDate.atStartOfWeek(context: Context): LocalDate {
    val locale = context.locale
    return with(WeekFields.of(locale).dayOfWeek(), 1)
}

fun LocalDate.atEndOfWeek(context: Context): LocalDate {
    val locale = context.locale
    return with(WeekFields.of(locale).dayOfWeek(), 7)
}