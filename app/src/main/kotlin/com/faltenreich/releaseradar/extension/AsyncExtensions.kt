package com.faltenreich.releaseradar.extension

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Any.doAfter(millis: Long, action: () -> Unit) = GlobalScope.launch {
    delay(millis)
    action()
}