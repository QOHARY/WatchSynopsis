package com.example.uas.watchsynopsis.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.uas.watchsynopsis.domain.Title
import com.example.uas.watchsynopsis.presentation.screens.ReleasesScreen
import com.example.uas.watchsynopsis.presentation.util.PreferenceHelper
import com.example.uas.watchsynopsis.presentation.viewmodels.ReleasesViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal const val releasesListNavigationRoutes = "releases"

fun NavGraphBuilder.releasesListScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    onMovieClick: (Title) -> Unit
) {
    FirebaseCrashlytics.getInstance().log("User action logged: ReleasesScreenNavigation.kt")
    composable(releasesListNavigationRoutes) {
        val viewModel: ReleasesViewModel = viewModel()
        val state = viewModel.uiState.collectAsState().value

        ReleasesScreen(
            uiState = state,
            navController = navController,
            preferenceHelper = preferenceHelper,
            onMovieClick = onMovieClick
        )
    }
}

fun NavController.navigateToReleasesList(
    navOptions: NavOptions? = null
) {
    Firebase.analytics.logEvent("navigate_to_releases_list") {
        param("screen", "ReleasesScreen")
    }
    navigate(releasesListNavigationRoutes, navOptions)
}
