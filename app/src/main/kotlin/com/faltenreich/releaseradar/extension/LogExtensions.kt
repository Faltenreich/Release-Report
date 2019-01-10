package com.faltenreich.releaseradar.extension

import android.util.Log

fun Any.log(message: String, logLevel: LogLevel = LogLevel.DEBUG) = javaClass.simpleName.let { tag ->
    when (logLevel) {
        LogLevel.VERBOSE -> Log.v(tag, message)
        LogLevel.DEBUG -> Log.d(tag, message)
        LogLevel.INFO -> Log.i(tag, message)
        LogLevel.WARN -> Log.w(tag, message)
        LogLevel.ERROR -> Log.e(tag, message)
    }
}

enum class LogLevel {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR
}