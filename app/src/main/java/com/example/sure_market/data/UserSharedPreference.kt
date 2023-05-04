package com.example.sure_market.data

import android.content.Context
import android.content.SharedPreferences

class UserSharedPreference(context: Context) {
    private val prefFilename = "userPref"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)

    fun getUserPref(userId: String, defaultValue: Long = 0L): Long = prefs.getInt(userId,
        defaultValue.toInt()
    ).toLong()


    fun setUserPrefs(responseData: ResponseUserId) {
        prefs.edit().putLong("userId", responseData.userId).apply()
    }

    // api 통신전
    fun setUserPrefs(userId: Long) {
        prefs.edit().putLong("userId", userId).apply()
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}