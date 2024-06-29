package com.example.uas.watchsynopsis.presentation

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        Firebase.crashlytics.log("message")
    }
}