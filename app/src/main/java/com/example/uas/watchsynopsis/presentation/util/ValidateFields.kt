package com.example.uas.watchsynopsis.presentation.util

import com.example.uas.watchsynopsis.data.api.firebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

fun validateEmail(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

    return when {
        email.isEmpty() -> { // Email is empty
            FirebaseCrashlytics.getInstance().log("Email validation failed: email is empty")
            firebaseAnalytics.logEvent("email_validation_failed") {
                param("reason", "email is empty")
            }
            false
        }
        !emailRegex.matches(email) -> { // Email format is invalid
            FirebaseCrashlytics.getInstance().log("Email validation failed: invalid format for email $email")
            firebaseAnalytics.logEvent("email_validation_failed") {
                param("reason", "invalid format")
                param("email", email)
            }
            false
        }
        else -> { // Email is valid
            FirebaseCrashlytics.getInstance().log("Email validation succeeded for email $email")
            firebaseAnalytics.logEvent("email_validation_succeeded") {
                param("email", email)
            }
            true
        }
    }
}

fun validatePassword(password: String): Boolean {
    val minLength = 8
    val maxLength = 25

    val hasUppercase = password.any { it.isUpperCase() }
    val hasNumber = password.any { it.isDigit() }

    val isValid = password.length in minLength..maxLength && hasUppercase && hasNumber

    if (!isValid) {
        FirebaseCrashlytics.getInstance().log("Password validation failed for password: $password")
        firebaseAnalytics.logEvent("password_validation_failed") {
            param("password", password)
        }
    } else {
        FirebaseCrashlytics.getInstance().log("Password validation succeeded for password: $password")
        firebaseAnalytics.logEvent("password_validation_succeeded") {
            param("password", password)
        }
    }
    return isValid
}