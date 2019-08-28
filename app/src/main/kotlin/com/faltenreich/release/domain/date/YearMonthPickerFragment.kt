package com.faltenreich.release.domain.date

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.whiteelephant.monthpicker.MonthPickerDialog
import org.threeten.bp.YearMonth

class YearMonthPickerFragment : DialogFragment(), MonthPickerDialog.OnDateSetListener {
    private val yearMonth by lazy { arguments?.getSerializable(ARGUMENT_YEAR_MONTH) as? YearMonth }

    var onValueChanged: ((YearMonth) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val yearMonth = yearMonth ?: YearMonth.now()
        return MonthPickerDialog.Builder(context, this, yearMonth.year, yearMonth.monthValue - 1).build()
    }

    override fun onDateSet(selectedMonth: Int, selectedYear: Int) {
        val yearMonth = YearMonth.of(selectedYear, selectedMonth + 1)
        onValueChanged?.invoke(yearMonth)
    }

    companion object {
        private const val ARGUMENT_YEAR_MONTH = "yearMonth"

        fun newInstance(yearMonth: YearMonth?): YearMonthPickerFragment {
            return YearMonthPickerFragment().also { fragment ->
                fragment.arguments = Bundle().apply { putSerializable(ARGUMENT_YEAR_MONTH, yearMonth) }
            }
        }
    }
}