package com.faltenreich.release.domain.date

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import org.threeten.bp.LocalDateTime

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private val hour by lazy { arguments?.getInt(ARGUMENT_HOUR) }
    private val minute by lazy { arguments?.getInt(ARGUMENT_MINUTE) }

    var onValueChanged: ((hour: Int, minute: Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val now = LocalDateTime.now()
        return TimePickerDialog(
            requireContext(),
            this,
            hour ?: now.hour,
            minute ?: now.minute,
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onValueChanged?.invoke(hourOfDay, minute)
    }

    companion object {
        private const val ARGUMENT_HOUR = "hour"
        private const val ARGUMENT_MINUTE = "minute"

        fun newInstance(hour: Int, minute: Int): TimePickerFragment {
            return TimePickerFragment().also { fragment ->
                fragment.arguments = Bundle().apply {
                    putInt(ARGUMENT_HOUR, hour)
                    putInt(ARGUMENT_MINUTE, minute)
                }
            }
        }
    }
}