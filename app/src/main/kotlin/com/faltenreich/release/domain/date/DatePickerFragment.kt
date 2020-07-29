package com.faltenreich.release.domain.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.faltenreich.release.base.date.Now
import org.threeten.bp.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val date by lazy { arguments?.getSerializable(ARGUMENT_DATE) as? LocalDate }

    var onValueChanged: ((LocalDate) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = date ?: Now.localDate()
        return DatePickerDialog(requireContext(), this, date.year, date.monthValue - 1, date.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, month + 1, dayOfMonth)
        onValueChanged?.invoke(date)
    }

    companion object {
        private const val ARGUMENT_DATE = "date"

        fun newInstance(date: LocalDate?): DatePickerFragment {
            return DatePickerFragment().also { fragment ->
                fragment.arguments = Bundle().apply { putSerializable(ARGUMENT_DATE, date) }
            }
        }
    }
}