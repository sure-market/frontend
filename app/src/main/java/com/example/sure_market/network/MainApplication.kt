package com.example.sure_market.network

import android.app.Application
import com.example.sure_market.data.UserSharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        lateinit var prefs: UserSharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserSharedPreference(applicationContext)
    }
}