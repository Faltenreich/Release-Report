package com.faltenreich.release.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.faltenreich.release.extension.asLocalDate
import com.faltenreich.release.extension.asYearMonth
import org.threeten.bp.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val date by lazy { arguments?.let { arguments -> DatePickerFragmentArgs.fromBundle(arguments).date?.asLocalDate } }
    private val yearMonth by lazy { arguments?.let { arguments -> DatePickerFragmentArgs.fromBundle(arguments).yearMonth?.asYearMonth } }

    var onDateSet: ((LocalDate?) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = date ?: LocalDate.now()
        return DatePickerDialog(requireContext(), this, date.year, date.monthValue - 1, date.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, month + 1, dayOfMonth)
        onDateSet?.invoke(date)
        // TODO: Propagate date change to potential observers
    }
}