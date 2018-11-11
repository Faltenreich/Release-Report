package com.faltenreich.releaseradar.data.provider

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

interface ViewModelCreator {
    fun <T : ViewModel> createViewModel(clazz: KClass<T>): T
}