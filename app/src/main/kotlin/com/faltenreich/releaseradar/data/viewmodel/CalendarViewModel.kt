package com.faltenreich.releaseradar.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate

class CalendarViewModel : ViewModel() {

    private val dateLiveData = MutableLiveData<LocalDate>()

    var date: LocalDate?
        get() = dateLiveData.value
        set(value) = dateLiveData.postValue(value)
}