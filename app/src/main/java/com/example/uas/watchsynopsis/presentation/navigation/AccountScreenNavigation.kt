package com.example.uas.watchsynopsis.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.uas.watchsynopsis.presentation.screens.AccountScreen
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal const val accountScreenRoute = "account"

fun NavGraphBuilder.accountScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    onLogout: () -> Unit
) {
    FirebaseCrashlytics.getInstance().log("User action logged: AccountScreenNavigation.kt")
    composable(accountScreenRoute) {
        AccountScreen(navController = navController, preferenceHelper = preferenceHelper, onLogout = onLogout)
    }
}

fun NavController.navigateToAccountScreen(
    navOptions: NavOptions? = null
) {
    Firebase.analytics.logEvent("navigate_to_account_screen") {
        param("screen", "AccountScreen")
    }
    navigate(accountScreenRoute, navOptions)
}
