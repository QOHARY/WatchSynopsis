package com.example.uas.watchsynopsis.presentation.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setUserLoggedIn(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.apply()

        FirebaseCrashlytics.getInstance().log("PreferenceHelper, saveUserLoggedIn: $email")
        firebaseAnalytics.logEvent("set_user_logged_in") {
            param("user_email", email)
        }
    }

    fun getUserLoggedIn(): String? {
        val email = sharedPreferences.getString("user_email", null)
        FirebaseCrashlytics.getInstance().log("PreferenceHelper, getUserLoggedIn: $email")
        firebaseAnalytics.logEvent("get_user_logged_in") {
            param("user_email", email ?: "")
        }
        return email
    }

    fun clearUserLoggedIn() {
        val editor = sharedPreferences.edit()
        editor.remove("user_email")
        editor.apply()
        FirebaseCrashlytics.getInstance().log("PreferenceHelper, clearUserLoggedIn")
        firebaseAnalytics.logEvent("clear_user_logged_in") {
            param("event", "clear_user_logged_in")
        }
    }
}
