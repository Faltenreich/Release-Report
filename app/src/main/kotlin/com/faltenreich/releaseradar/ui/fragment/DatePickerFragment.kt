package com.faltenreich.releaseradar.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.faltenreich.releaseradar.R
import org.threeten.bp.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val givenDate by lazy { arguments?.getSerializable(DATE_GIVEN) as? LocalDate }

    var onDateSet: ((LocalDate?) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = givenDate ?: LocalDate.now()
        return DatePickerDialog(context, R.style.DateTimePicker, this, date.year, date.monthValue - 1, date.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDateSet?.invoke(LocalDate.of(year, month + 1, dayOfMonth))
    }

    companion object {
        private const val DATE_GIVEN = "givenDate"

        fun newInstance(date: LocalDate? = null, onDateSet: (LocalDate?) -> Unit): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.onDateSet = onDateSet
            fragment.arguments = Bundle().apply { putSerializable(DATE_GIVEN, date) }
            return fragment
        }
    }
}