package com.example.a3tracker

import android.app.Application
import com.example.a3tracker.manager.SharedPreferencesManager

class App : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPreferencesManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = SharedPreferencesManager(applicationContext)
    }
}