package com.faltenreich.releaseradar.ui.view

import android.content.Context
import com.whiteelephant.monthpicker.MonthPickerDialog
import org.threeten.bp.LocalDate

object MonthPicker {

    private val MAX_YEAR by lazy { LocalDate.now().plusYears(30).year }

    fun show(context: Context?, currentDate: LocalDate, onDateSet: (LocalDate) -> Unit) {
        val listener = MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->
            onDateSet(currentDate.withMonth(selectedMonth + 1).withYear(selectedYear))
        }
        MonthPickerDialog.Builder(context, listener, currentDate.year, currentDate.monthValue - 1).apply {
            setMaxYear(MAX_YEAR)
        }.build().show()
    }
}