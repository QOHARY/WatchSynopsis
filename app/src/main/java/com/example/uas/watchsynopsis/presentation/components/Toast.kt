package com.example.uas.watchsynopsis.presentation.components

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

fun showToast(context: Context, message: String) {
    val firebaseAnalytics = Firebase.analytics
    FirebaseCrashlytics.getInstance().log("Showing toast message: $message")
    firebaseAnalytics.logEvent("show_toast") {
        param("toast_message", message)
        param("screen", "showToast")
    }
    //Show message Toast
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}