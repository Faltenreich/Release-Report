package com.faltenreich.releaseradar.ui.view

import android.content.Context
import com.whiteelephant.monthpicker.MonthPickerDialog
import org.threeten.bp.LocalDate

object MonthPicker {
    private const val MAX_YEAR_OFFSET = 20L
    private val MAX_YEAR by lazy { LocalDate.now().plusYears(MAX_YEAR_OFFSET).year }

    fun show(context: Context?, givenDate: LocalDate, onDateSet: (LocalDate) -> Unit) {
        // MonthPickerDialog has zero-based months
        val listener = MonthPickerDialog.OnDateSetListener { month, year -> onDateSet(givenDate.withMonth(month + 1).withYear(year)) }
        val year = givenDate.year
        val month = givenDate.monthValue - 1
        MonthPickerDialog.Builder(context, listener, year, month).apply {
            setMaxYear(MAX_YEAR)
        }.build().show()
    }
}