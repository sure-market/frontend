package com.example.sure_market.data

import android.content.Context
import android.content.SharedPreferences

class UserSharedPreference(context: Context) {
    private val prefFilename = "userPref"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)

    fun getUserPref(userToken: String, defaultValue: String = ""): String? =
        prefs.getString(userToken, defaultValue)


    fun setUserPrefs(userToken: String) {
        prefs.edit().putString("userToken", userToken).apply()
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}