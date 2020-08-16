package com.example.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

class Prefs(context: Context) {
    companion object {
        val PREFS_FILENAME = "prefs"
        val BACKGROUND_COLOR = "background_color"
        val KEY_THEME = "theme"
    }
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var bgColor: Int
        get() = prefs.getInt(BACKGROUND_COLOR, Color.BLACK)
        set(value) = prefs.edit().putInt(BACKGROUND_COLOR, value).apply()

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, def: String?): String? = prefs.getString(key, def)
}