package com.faltenreich.releaseradar.data.preference

import android.content.Context
import android.content.SharedPreferences

abstract class SharedPreferences {

    @PublishedApi internal lateinit var preferences: SharedPreferences

    fun init(context: Context, name: String = context.packageName) {
        preferences = context.getSharedPreferences("$name.prefs", Context.MODE_PRIVATE)
    }

    inline fun SharedPreferences.edit(function: SharedPreferences.Editor.() -> Unit): Unit = with(edit()) { function(); apply() }

    @PublishedApi
    @Suppress("IMPLICIT_CAST_TO_ANY")
    internal inline fun <reified T : Comparable<T>> SharedPreferences.get(key: String, defaultValue: T? = null): T {
        return when (T::class) {
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: DEFAULT_VALUE_BOOLEAN)
            Int::class -> getInt(key, defaultValue as? Int ?: DEFAULT_VALUE_INT)
            Float::class -> getFloat(key, defaultValue as? Float ?: DEFAULT_VALUE_FLOAT)
            Long::class -> getLong(key, defaultValue as? Long ?: DEFAULT_VALUE_LONG)
            String::class -> getString(key, defaultValue as? String ?: DEFAULT_VALUE_STRING)
            else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
        } as T
    }

    @PublishedApi
    internal inline fun <reified T : Comparable<T>> SharedPreferences.Editor.set(pair: Pair<String, T?>) = when (T::class) {
        Boolean::class -> putBoolean(pair.first, pair.second as? Boolean ?: DEFAULT_VALUE_BOOLEAN)
        Int::class -> putInt(pair.first, pair.second as? Int ?: DEFAULT_VALUE_INT)
        Float::class -> putFloat(pair.first, pair.second as? Float ?: DEFAULT_VALUE_FLOAT)
        Long::class -> putLong(pair.first, pair.second as? Long ?: DEFAULT_VALUE_LONG)
        String::class -> putString(pair.first, pair.second as? String ?: DEFAULT_VALUE_STRING)
        else -> throw IllegalArgumentException("Unsupported type: ${pair.second?.let { value -> value::class} ?: "Unknown" }")
    }

    inline fun <reified T : Comparable<T>> get(key: String, defaultValue: T? = null): T = preferences.get(key, defaultValue)

    inline fun <reified T : Comparable<T>> set(pair: Pair<String, T?>): Unit = preferences.edit { set(pair) }

    fun getStringSet(key: String, defaultValue: Set<String> = DEFAULT_VALUE_SET): Set<String> = preferences.getStringSet(key, defaultValue) ?: emptySet()

    fun setStringSet(pair: Pair<String, Set<String>>) = preferences.edit { putStringSet(pair.first, pair.second).apply() }


    companion object {
        const val DEFAULT_VALUE_BOOLEAN = false
        const val DEFAULT_VALUE_INT = -1
        const val DEFAULT_VALUE_FLOAT = -1f
        const val DEFAULT_VALUE_LONG = -1L
        const val DEFAULT_VALUE_STRING = ""
        val DEFAULT_VALUE_SET = setOf<String>()
    }
}
