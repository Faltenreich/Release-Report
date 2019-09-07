package com.faltenreich.release.domain.date

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import org.threeten.bp.LocalTime

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    
    private val time: LocalTime? by lazy { arguments?.getSerializable(ARGUMENT_TIME) as? LocalTime }

    var onValueChanged: ((LocalTime) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = time ?: LocalTime.now()
        return TimePickerDialog(
            requireContext(),
            this,
            time.hour,
            time.minute,
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onValueChanged?.invoke(LocalTime.of(hourOfDay, minute))
    }

    companion object {
        
        private const val ARGUMENT_TIME = "time"

        fun newInstance(time: LocalTime): TimePickerFragment {
            return TimePickerFragment().also { fragment ->
                fragment.arguments = Bundle().apply { putSerializable(ARGUMENT_TIME, time) }
            }
        }
    }
}