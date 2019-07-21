package com.faltenreich.release.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.faltenreich.release.extension.asYearMonth
import com.whiteelephant.monthpicker.MonthPickerDialog
import org.threeten.bp.YearMonth

class YearMonthPickerFragment : DialogFragment(), MonthPickerDialog.OnDateSetListener {
    private val yearMonth by lazy { arguments?.let { arguments -> YearMonthPickerFragmentArgs.fromBundle(arguments).yearMonth?.asYearMonth } }

    var onValueChanged: ((YearMonth?) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val yearMonth = yearMonth ?: YearMonth.now()
        return MonthPickerDialog.Builder(context, this, yearMonth.year, yearMonth.monthValue - 1).build()
    }

    override fun onDateSet(selectedMonth: Int, selectedYear: Int) {
        val yearMonth = YearMonth.of(selectedYear, selectedMonth + 1)
        onValueChanged?.invoke(yearMonth)
    }
}