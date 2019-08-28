package com.faltenreich.release.base.log

import android.util.Log

val Any.tag: String
    get() = this::class.java.simpleName

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